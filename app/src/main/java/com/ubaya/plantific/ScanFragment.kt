package com.ubaya.plantific

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.ubaya.plantific.ml.Densenet121Model1
import com.ubaya.plantific.ml.Mobilenetv2Modelfix
import com.ubaya.plantific.ml.Vgg16Model1
import kotlinx.android.synthetic.main.fragment_scan.*
import kotlinx.android.synthetic.main.fragment_scan.view.*
import org.json.JSONObject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


class ScanFragment : Fragment() {

    var bitmap: Bitmap? =null
    lateinit var sharedPref: SharedPreferences

    var iduser = ""
    var hasilprediksi: String = ""
    val PICK_IMAGE = 1
    val REQUEST_CODE = 200
    var v: View? = null
    var maxvalue=0f
    var idTanaman: String = ""
    var daun = arrayOf(
        "Afrika","Apel India","Belimbing Wuluh","Benahong","Brotowali",
        "Ginseng Jawa","Jahe Merah","Jambu Biji","Jeruk Nipis",
        "Kemangi","Kencur","Kumis kucing","Kunci","Kunir","Lidah Buaya",
        "Manggis","Pecut Kuda","Pepaya","Salam","Sambiloto",
        "Seledri","Sirih","Temu Ireng","Daun Ungu")
    var currentPhotoPath=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun launchImageCrop(uri: Uri) {
        context?.let {
            CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1920, 1080)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(it,this)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Image Capture Plantific", null)
        return Uri.parse(path)
    }
    private fun ARGBBitmap(img: Bitmap): Bitmap? {
        return img.copy(Bitmap.Config.ARGB_8888, true)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var uri: Uri? = data?.data
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 200) {
                uri = data?.getData()
                if (uri != null) {
                    launchImageCrop(uri)
                }
            }

            else if (requestCode == 201) {
                bitmap= data?.extras?.get("data") as Bitmap
                bitmap=ARGBBitmap(bitmap!!)
                imageViewPredict.setImageBitmap(bitmap)
//                uri=getImageUri(requireContext(),bitmap)
//                launchImageCrop(uri!!)
            }
            if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                val resultUri = result.uri
                imageViewPredict.setImageURI(resultUri)
                var resolver = requireActivity().contentResolver
                bitmap = MediaStore.Images.Media.getBitmap(resolver, resultUri)
            }
        }
    }

        fun insertHistory() {
            val q = Volley.newRequestQueue(this.context)
            val url = "https://plantific.ifubaya.id/insertHistory.php"
            var stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener {
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK") {
                        val action = ScanFragmentDirections.actionscantodetail(idTanaman, "1")
                        view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

                    }
                },
                Response.ErrorListener {
                    Log.d("inserthistory", it.message.toString())
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

        fun cekIDTanaman() {
            val q = Volley.newRequestQueue(this.context)
            val url = "https://plantific.ifubaya.id/scanIDTanaman.php"
            var stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener {
                    Log.d("hasilprediksi", hasilprediksi)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK") {
                        val data = obj.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            var data = data.getJSONObject(i)
                            idTanaman = data.getString("idTanaman")
                            var idLogin = sharedPref.getString(key_id, "")
                            if(idLogin!="0")
                            {
                                insertHistory()
                            }
                            else
                            {
                                val action = ScanFragmentDirections.actionscantodetail(idTanaman, "1")
                                view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
                            }
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
        fun checkForPermissions(permission: String,name:String,requestCode: Int)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                when{
                    ContextCompat.checkSelfPermission(requireContext(),permission)==PackageManager.PERMISSION_GRANTED->{
                        Toast.makeText(requireContext(),"$name permission granted",Toast.LENGTH_SHORT).show()
                    }
                    shouldShowRequestPermissionRationale(permission)->showDialog(permission,name,requestCode)
                    else->ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),requestCode)
                }
            }
        }
    private fun requestForSpecificPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            101
        )
    }
    private fun checkIfAlreadyhavePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        return if (result == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name:String)
        {
            if(grantResults.isEmpty()||grantResults[0]!=PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(requireContext(),"$name permission refused",Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(requireContext(),"$name permission granted",Toast.LENGTH_SHORT).show()
            }
        }
        when(requestCode)
        {
            REQUEST_CODE->innerCheck("camera")
        }
    }
        fun showDialog(permission: String,name: String,requestCode: Int)
        {
            val builder=AlertDialog.Builder(requireContext())

            builder.apply {
                setMessage("Permission to access your $name is required to use this app")
                setTitle("Permission Required")
                setPositiveButton("OK"){dialog,which->
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),requestCode)
                }
            }
            val dialog:AlertDialog=builder.create()
            dialog.show()
        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            var v = inflater.inflate(R.layout.fragment_scan, container, false)
            sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
            var threshold=sharedPref.getString(threshold, "")
            var modell=sharedPref.getString(model, "")
            sharedPref = requireActivity().getSharedPreferences(pref, Context.MODE_PRIVATE)
            sharedPref.getString(key_id, "")?.let {
                iduser = it
                Log.d("id", it)
            }.toString()
            v.btnLoadImageScan.setOnClickListener { view ->
                val REQUEST_CODE = 200
                var intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE)
            }
            v.btnTakeImage.setOnClickListener { view ->
                val MyVersion = Build.VERSION.SDK_INT
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (!checkIfAlreadyhavePermission()) {
                        requestForSpecificPermission()
                    }
                    else
                    {
                        val REQUEST_CODE = 201
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, REQUEST_CODE)
                    }
                }
//                checkForPermissions(android.Manifest.permission.CAMERA,"camera",REQUEST_CODE)
//                val rxPermissions = RxPermissions(this)
//                rxPermissions
//                    .request(Manifest.permission.CAMERA)
//                    .subscribe { granted ->
//                        if (granted) { // Always true pre-M
                            // I can control the camera now

//                        } else {
//                            // Oups permission denied
//                        }
//                    }


            }
            v.btnPredict.setOnClickListener { view ->
                var imageProcessor =
                    ImageProcessor.Builder()
                        .add(ResizeOp(200, 200, ResizeOp.ResizeMethod.BILINEAR))
                        .add(NormalizeOp(0.0f, 255.0f))
                        .build()
                if(modell=="mobilenet")
                {
                    Log.d("modelklasifikasi","masuk mobilenet")
                    val modelklasifikasi=Mobilenetv2Modelfix.newInstance(requireContext())
                    val inputFeature0 =
                        TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)
                    val input = bitmap?.let { Bitmap.createScaledBitmap(it, 200, 200, true) }
                    var image = TensorImage(DataType.FLOAT32)
                    if(bitmap==null)
                    {
                        val dialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        dialog.setTitleText("Gagal")
                        dialog.setContentText("Anda Belum Memilih / Mengambil Gambar Tanaman")
                        dialog.setConfirmText("OK")
                        dialog.show()
                    }
                    else
                    {
                        image.load(input)
                        image = imageProcessor.process(image)
                        val byteBuffer: ByteBuffer = image.buffer
                        inputFeature0.loadBuffer(byteBuffer)
                        val outputs = modelklasifikasi.process(inputFeature0)
                        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                        var max = getMax(outputFeature0.floatArray)
                        maxvalue=(outputFeature0.floatArray[max])
                        hasilprediksi = daun[max]
                        modelklasifikasi.close()
                        if(maxvalue< threshold!!.toFloat())
                        {
                            txtnotepredict.text="Input Gambar Tidak Dapat Dikenali. Tips : Pastikan memilih gambar / mengambil gambar dengan fokus daun dengan background polos untuk hasil yang lebih baik  "
                        }
                        else
                        {
                            cekIDTanaman()
                        }
                    }
                }
                else if(modell=="vgg16")
                {
                    Log.d("modelklasifikasi","masuk vgg 1")
                    val modelklasifikasi=Vgg16Model1.newInstance(requireContext())
                    val inputFeature0 =
                        TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)
                    val input = bitmap?.let { Bitmap.createScaledBitmap(it, 200, 200, true) }
                    var image = TensorImage(DataType.FLOAT32)
                    if(bitmap==null)
                    {
                        val dialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        dialog.setTitleText("Gagal")
                        dialog.setContentText("Anda Belum Memilih / Mengambil Gambar Tanaman")
                        dialog.setConfirmText("OK")
                        dialog.show()
                    }
                    else
                    {
                        image.load(input)
                        image = imageProcessor.process(image)
                        val byteBuffer: ByteBuffer = image.buffer
                        inputFeature0.loadBuffer(byteBuffer)
                        val outputs = modelklasifikasi.process(inputFeature0)
                        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                        var max = getMax(outputFeature0.floatArray)
                        maxvalue=(outputFeature0.floatArray[max])
                        hasilprediksi = daun[max]
                        modelklasifikasi.close()
                        if(maxvalue< threshold!!.toFloat())
                        {
                            txtnotepredict.text="Input Gambar Tidak Dapat Dikenali. Tips : Pastikan memilih gambar / mengambil gambar dengan fokus daun dengan background polos untuk hasil yang lebih baik  "
                        }
                        else
                        {
                            cekIDTanaman()
                        }
                    }
                }
                else if(modell=="densenet121")
                {
                    Log.d("modelklasifikasi","masuk densenet 2")
                    val modelklasifikasi=Densenet121Model1.newInstance(requireContext())
                    val inputFeature0 =
                        TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)
                    val input = bitmap?.let { Bitmap.createScaledBitmap(it, 200, 200, true) }
                    var image = TensorImage(DataType.FLOAT32)
                    if(bitmap==null)
                    {
                        val dialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        dialog.setTitleText("Gagal")
                        dialog.setContentText("Anda Belum Memilih / Mengambil Gambar Tanaman")
                        dialog.setConfirmText("OK")
                        dialog.show()
                    }
                    else
                    {
                        image.load(input)
                        image = imageProcessor.process(image)
                        val byteBuffer: ByteBuffer = image.buffer
                        inputFeature0.loadBuffer(byteBuffer)
                        val outputs = modelklasifikasi.process(inputFeature0)
                        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                        var max = getMax(outputFeature0.floatArray)
                        maxvalue=(outputFeature0.floatArray[max])
                        hasilprediksi = daun[max]
                        Log.d("hasilprediksi", max.toString())
                        modelklasifikasi.close()
                        if(maxvalue< threshold!!.toFloat())
                        {
                            txtnotepredict.text="Input Gambar Tidak Dapat Dikenali. Tips : Pastikan memilih gambar / mengambil gambar dengan fokus daun dengan background polos untuk hasil yang lebih baik  "
                        }
                        else
                        {
                            cekIDTanaman()
                        }
                    }
                }
                Log.d("threshold", threshold.toString())
                Log.d("hasilprediksi", hasilprediksi.toString())
                Log.d("hasilprediksi", maxvalue.toString())

            }


            return v
            // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_scan, container, false)
        }


    }
