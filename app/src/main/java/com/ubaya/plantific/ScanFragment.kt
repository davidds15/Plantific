package com.ubaya.plantific

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.plantific.ml.Mobilenetv2Model
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.fragment_scan.view.*
import org.json.JSONObject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class ScanFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var bitmap:Bitmap
    private lateinit var button:Button
    lateinit var sharedPref : SharedPreferences
    var iduser=""
    var hasilprediksi:String=""
    val PICK_IMAGE = 1
    val REQUEST_CODE = 200
    var v:View ?= null
    var idTanaman:String=""
    var daun= arrayOf("Afrika", "Apel India", "Belimbing Wuluh", "Benahong", "Brotowali","Daun Ungu", "Ginseng Jawa", "Jahe Merah", "Jambu Biji", "Jeruk Nipis",
        "Kemangi", "Kencur", "Kumis kucing", "Kunci", "Kunir", "Lidah Buaya", "Manggis",
        "Pecut Kuda", "Pepaya", "Salam", "Sambiloto", "Seledri", "Sirih", "Temu Ireng")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        var imageview=v?.findViewById<ImageView>(R.id.imageViewPredict)
//        imageview?.setImageURI(data?.data)
        var uri:Uri?=data?.data

        if(resultCode == RESULT_OK && data !=null ){
            if(requestCode==200)
            {
                uri = data?.getData()
                imageViewPredict.setImageURI(uri)
                var resolver = requireActivity().contentResolver
                bitmap=MediaStore.Images.Media.getBitmap(resolver,uri)
            }
            else if (requestCode==201)
            {
                uri = data?.getData()
                imageViewPredict.setImageURI(uri)
                var resolver = requireActivity().contentResolver
                bitmap=MediaStore.Images.Media.getBitmap(resolver,uri)
            }

        }




    }

fun insertHistory() {
    val q = Volley.newRequestQueue(this.context)
    val url =  "http://192.168.0.103/Plantific/insertHistory.php"
    var stringRequest = object : StringRequest(
        Request.Method.POST, url,
        Response.Listener {
            val obj = JSONObject(it)
            if(obj.getString("result") == "OK") {
                    val action = ScanFragmentDirections.actionscantodetail(idTanaman,"1")
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

            }
        },
        Response.ErrorListener {
            Log.d("cekparams", it.message.toString())
        }) {
        override fun getParams(): MutableMap<String, String> {
            val params = HashMap<String, String>()
            params["namaTanaman"] = hasilprediksi
            params["iduser"] = iduser
            params["idtanaman"] = idTanaman
            return params
        }
    }
    q.add(stringRequest)
}
    fun bacadata() {
        val q = Volley.newRequestQueue(this.context)
        val url =  "http://192.168.0.103/Plantific/scanIDTanaman.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Log.d("cekparams", hasilprediksi)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        var data = data.getJSONObject(i)
                        idTanaman= data.getString("idTanaman")
                        Log.d("idtanamanbacadata",idTanaman)
//                        val action = ScanFragmentDirections.actionscantodetail(idTanaman,"1")
//                        view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
                        insertHistory()
                    }
                }
            },
            Response.ErrorListener {
                Log.d("cekparams", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["namaTanaman"] = hasilprediksi
                return params
            }
        }
        q.add(stringRequest)
}

    fun getMax(arr: FloatArray): Int {
        var idx = 0;
        var max = 0.0f
        for (i in 0..arr.count() - 1) {
            if (arr[i] >= max) {
                max = arr[i]
                idx = i
            }
        }
        return idx
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_scan, container, false)
        sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
        sharedPref.getString(key_id,"")?.let {
            iduser=it
            Log.d("id", it) }.toString()
        view.btnLoadImageScan.setOnClickListener { view ->
            val REQUEST_CODE = 200
            var intent:Intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"
            startActivityForResult(intent,REQUEST_CODE)
        }
        view.btnTakeImage.setOnClickListener { view ->
            val REQUEST_CODE = 201
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, REQUEST_CODE)
        }
        view.btnPredict.setOnClickListener { view ->
            var imageProcessor =
                ImageProcessor.Builder()
                    .add(ResizeOp(200, 200, ResizeOp.ResizeMethod.BILINEAR))
                    .add(NormalizeOp(0.0f,255.0f))
                    .build()
            val model = Mobilenetv2Model.newInstance(requireContext())

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)

            val input = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
            var image = TensorImage(DataType.FLOAT32)
            image.load(input)
            image = imageProcessor.process(image)
            val byteBuffer: ByteBuffer = image.buffer
            inputFeature0.loadBuffer(byteBuffer)
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max = getMax(outputFeature0.floatArray)
            hasilprediksi=daun[max]
            txtResultPredict.setText(max.toString())
            txtResultPredict2.setText(hasilprediksi)

// Releases model resources if no longer used.
            model.close()
            bacadata()

        }

        // Return the fragment view/layout


        return view
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_scan, container, false)
    }


}