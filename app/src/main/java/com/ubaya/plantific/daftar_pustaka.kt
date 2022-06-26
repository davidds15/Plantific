package com.ubaya.plantific

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_daftar_pustaka.*
import kotlinx.android.synthetic.main.fragment_daftar_pustaka.view.*


class daftar_pustaka : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var daun = arrayOf(
            "Afrika","Apel India","Belimbing Wuluh","Benahong","Brotowali",
            "Daun Ungu","Ginseng Jawa","Jahe Merah","Jambu Biji","Jeruk Nipis",
            "Kemangi","Kencur","Kumis kucing","Kunci","Kunir","Lidah Buaya",
            "Manggis","Pecut Kuda","Pepaya","Salam","Sambiloto",
            "Seledri","Sirih","Temu Ireng")
        // Inflate the layout for this fragment
        var v=inflater.inflate(R.layout.fragment_daftar_pustaka, container, false)
        v.txtUngu.text=
        "Afrika : \n" +
                "https://intip.in/PustakaAfrika1\n" +
                "https://intip.in/PustakaAfrika2\n" +
                "https://intip.in/PustakaAfrika3\n" +
                "https://intip.in/PustakaAfrika4" +
        "\n\nApel India : \n" +
                "https://intip.in/PustakaApelIndia1\n" +
                "https://intip.in/PustakaApelIndia2\n" +
                "https://intip.in/PustakaApelIndia3"+
        "\n\nBelimbing Wuluh : \n" +
                "https://intip.in/PustakaBelimbingWuluh1\n" +
                "https://intip.in/PustakaBelimbingWuluh2\n" +
                "Aseptianova, A., & Yuliany, E. H. (2020). Penyuluhan Manfaat Belimbing Wuluh (Averrhoa bilimbi Linn.) sebagai Tanaman Kesehatan di Kelurahan Kebun Bunga, Kecamatan Sukarami, Palembang. Abdihaz: Jurnal Ilmiah Pengabdian Pada Masyarakat, 2(2), 52. https://doi.org/10.32663/abdihaz.v2i2.910\n" +
                "Insan, R. R., Faridah, A., Yulastri, A., & Holinesti, R. (2019). Using Belimbing Wuluh (Averhoa blimbi L.) As A Functional Food Processing Product. Jurnal Pendidikan Tata Boga Dan Teknologi, 1(1), 47–55." +
        "\n\nBenahong : \n " +
                "https://intip.in/PustakaBenahong1\n" +
                "https://intip.in/PustakaBenahong2\n" +
                "https://intip.in/PustakaBenahong3\n" +
                "Desy, R., & Nova, A. (2018). PEMBINAAN MASYARAKAT TENTANG PEMANFAATAN TANAMAN BINAHONG (Anredera cordifolia) SEBAGAI OBAT TRADISIONAL DIGAMPONG SIDOREJO LANGSA LAMA. Jurnal Jeumpa, 5(2), 112–118. \n" +
                "Ariani, S. (2014). KHASIAT DAUN BINAHONG (Anredera cordifolia (Ten.) Steenis) TERHADAP PEMBENTUKAN JARINGAN GRANULASI DAN REEPITELISASI PENYEMBUHAN LUKA TERBUKA KULIT KELINCI. Jurnal E-Biomedik, 1(2). https://doi.org/10.35790/ebm.1.2.2013.3250" +
        "\n\nBrotowali : \n" +
                "https://intip.in/PustakaBrotowali1\n" +
                "https://intip.in/PustakaBrotowali2\n" +
                "Maylina, Alivia. (2019). Studi Katalitik Herbal Pemanfaatan Tanaman Brotowali (Tinospora Cordifolia) sebagai Obat Penurun Kadar Glukosa Darah (Diabetes Mellitus). 10.31227/osf.io/6syqv. " +
        "\n\nDaun Ungu : \n" +
                "https://intip.in/PustakaUngu1\n" +
                "https://intip.in/PustakaUngu2\n" +
                "https://intip.in/PustakaUngu3\n" +
                "https://intip.in/PustakaUngu4\n" +
        "\nGinseng Jawa : \n" +
                "https://intip.in/PustakaGinsengJawa1\n" +
                "https://intip.in/PustakaGinsengJawa2\n" +
                "https://intip.in/PustakaGinsengJawa3\n" +
                "https://intip.in/PustakaGinsengJawa4\n"+
                "Lestario et al, 2009 Agritech Vol. 29, No. 2, Juli 2009" +
        "\n\nJahe Merah : \n" +
                "https://intip.in/PustakaJaheMerah1\n" +
                "https://intip.in/PustakaJaheMerah2\n" +
                "https://intip.in/PustakaJaheMerah3\n" +
                "https://intip.in/PustakaJaheMerah4\n" +
                "Redi Aryanta, I. W. (2019). MANFAAT JAHE UNTUK KESEHATAN. Widya Kesehatan, 1(2), 39–43. https://doi.org/10.32795/widyakesehatan.v1i2.463 \n" +
                "Luhurningtyas, F. P., Susilo, J., Yuswantina, R., Widhihastuti, E., & Ardiyansah, F. W. (2021). Aktivitas Imunomodulator dan Kandungan Fenol Ekstrak Terpurifikasi Rimpang Jahe Merah (Zingiber officinale Rosc. Var.Rubrum). Indonesian Journal of Pharmacy and Natural Product, 4(1). https://doi.org/10.35473/ijpnp.v4i1.974\n" +
                "Sari, D., & Nasuha, A. (2021). Kandungan Zat Gizi, Fitokimia, dan Aktivitas Farmakologis pada Jahe (Zingiber officinale Rosc.). Tropical Bioscience: Journal of Biological Science Vol. 1, No. 2 (Desember 2021), 1(2), 11–18." +
        "\n\nJambu Biji : \n" +
                "https://intip.in/PustakaJambuBiji01\n" +
                "https://intip.in/PustakaJambuBiji2\n" +
                "https://intip.in/PustakaJambuBiji3\n"+
                "https://intip.in/PustakaJambuBiji4\n" +
                "Norlita, W., & KN, T. S. (2017). PEMANFAATAN JAMBU BIJI BAGI KESEHATAN PADA MASYARAKAT DI DESA SIALANG KUBANG KECAMATAN PERHENTIAN RAJA, KAMPAR. Photon: Jurnal Sain Dan Kesehatan, 7(02), 131–133. https://doi.org/10.37859/jp.v7i02.518" +
        "\n\nJeruk Nipis : \n" +
                "https://intip.in/PustakaJerukNipis1\n" +
                "https://intip.in/PustakaJerukNipis2\n" +
                "https://intip.in/PustakaJerukNipis3\n" +
                "https://intip.in/PustakaJerukNipis4\n" +
                "https://intip.in/PustakaJerukNipis5\n" +
                "https://intip.in/PustakaJerukNipis6"+
        "\n\nKemangi : \n" +
                "https://intip.in/PustakaKemangi1\n" +
                "https://intip.in/PustakaKemangi2\n" +
                "Wahid, A. R., Ittiqo, D. H., Qiyaam, N., Hati, M. P., Fitriana, Y., Amalia, A., & Anggraini, A. (2020). PEMANFAATAN DAUN KEMANGI (Ocinum sanctum) SEBAGAI PRODUK ANTISEPTIK UNTUK PREVENTIF PENYAKIT DI DESA BATUJAI KABUPATEN LOMBOK TENGAH. SELAPARANG Jurnal Pengabdian Masyarakat Berkemajuan, 4(1), 500. https://doi.org/10.31764/jpmb.v4i1.2841\n" +
                "Cahyani, N. M. E. (2014). DAUN KEMANGI (OCINUM CANNUM) SEBAGAI ALTERNATIF PEMBUATAN HANDSANITIZIER. Jurnal Kesehatan Masyarakat KEMAS, 9(2), 150–156. https://journal.unnes.ac.id/nju/index.php/kemas/article/viewFile/2843/2899"+
        "\n\nKencur : \n" +
                "https://intip.in/PustakaKencur1\n" +
                "https://intip.in/PustakaKencur2\n" +
                "https://intip.in/PustakaKencur3\n" +
                "Silalahi, M. (2019). KENCUR (Kaempferia galanga) DAN BIOAKTIVITASNYA. Jurnal Pendidikan Informatika Dan Sains, 8(1), 127. https://doi.org/10.31571/saintek.v8i1.1178\n" +
                "S., & Megantara, S. (2019). KARAKTERISTIK MORFOLOGI TANAMAN KENCUR (KAEMPFERIA GALANGA L.) DAN AKTIVITAS FARMAKOLOGI. Farmaka, 17(02), 256–262. " +
        "\n\nKumis Kucing : \n" +
                "https://intip.in/PustakaKumisKucing1\n" +
                "https://intip.in/PustakaKumisKucing2\n" +
                "https://intip.in/PustakaKumisKucing3\n" +
                "https://intip.in/PustakaKumisKucing4\n" +
                "https://intip.in/PustakaKumisKucing5"+
        "\n\nKunci : \n" +
                "https://intip.in/PustakaKunci1\n" +
                "https://intip.in/PustakaKunci2\n" +
                "https://intip.in/PustakaKunci3\n" +
                "Putranti, W., & Bachri, S. (2018). Uji Toksisitas Minyak Atsiri Rimpang Temu Kunci (Boesenbergia pandurata (Roxb) Schlecht) terhadap Larva Aedes aegypti serta Profil GC–MS. Traditional Medicine Journal, 23(02), 97–102." +
        "\n\nKunir : \n" +
                "https://intip.in/PustakaKunir1\n" +
                "https://intip.in/PustakaKunir2\n" +
                "https://intip.in/PustakaKunir3\n"+
                "https://intip.in/PustakaKunir4\n" +
                "Fannia Kusuma, Rosyidi, N. W., & Cahyati, S. (2019). Manfaat Kunyit (Curcuma longa) dalam Farmasi. Center for Open Science. https://doi.org/10.31227/osf.io/j9a34" +
        "\n\nLidah Buaya : \n" +
                "https://intip.in/PustakaLidahBuaya1\n" +
                "https://intip.in/PustakaLidahBuaya2\n" +
                "https://intip.in/PustakaLidahBuaya3\n" +
                "Dewi, D. W., Khotimah, S., & Liana, D. F. (2016). Pemanfaatan Infusa Lidah Buaya (Aloe vera L) sebagai Antiseptik Pembersih Tangan terhadap Jumlah Koloni Kuman. Jurnal Cerebellum, 2(3), 577–589. https://media.neliti.com/media/publications/189002-ID-pemanfaatan-infusa-lidah-buaya-aloe-vera.pdf\n" +
                "Marhaeni, L. S. (2020). POTENSI LIDAH BUAYA (Aloe vera Linn) SEBAGAI OBAT DAN SUMBER PANGAN. AGRISIA - Jurnal Ilmu-Ilmu Pertanian, 13(1), 32–39. https://ejournal.borobudur.ac.id/index.php/3/article/view/746" +

        "\n\nManggis : \n" +
                "https://intip.in/PustakaManggis1\n" +
                "https://intip.in/PustakaManggis2\n" +
                "Dr. Syakir, D. (2014). KHASIAT BUAH MANGGIS UNTUK KEHIDUPAN. Jurnal Al Hikmah, 15(1), 60–68. https://journal.uin-alauddin.ac.id/index.php/al_hikmah/article/view/373\n" +
                "Daulae, A. H. (2013). LIMBAH BUAH MANGGIS (Garcinia mangostana L.) PENUH KHASIAT BERPOTENSI JADI KEWIRAUSAHAAN DI SUMATERA UTARA. JURNAL Pengabdian Kepada Masyarakat Vol. 19 Nomor 72 Tahun XIX Juni 2013, 19(72), 1–8."+
        "\n\nPecut Kuda : \n" +
                "https://intip.in/PustakaPecutKuda1\n" +
                "https://intip.in/PustakaPecutKuda2\n" +
                "https://intip.in/PustakaPecutKuda3\n" +
                "Utami, K., Sari, I., & N. (2019). PENGARUH PEMBERIAN TOPIKAL EKSTRAK ETANOL DAUN PECUT KUDA (Stachytarpheta jamaicensis (L.) Vahl) TERHADAP PENYEMBUHAN LUKA TERBUKA PADA PUNGGUNG MENCIT. CHEMICA: Jurnal Pendidikan Kimia Dan Ilmu Kimia, 2(1), 21–27." +
        "\n\nPepaya : \n" +
                "https://intip.in/PustakaPepaya1\n" +
                "https://intip.in/PustakaPepaya2\n" +
                "Syah, A., Dianita, P. S., & Agusta, H. F. (2022). EFEKTIVITAS TANAMAN PEPAYA (Carica papaya L.) TERHADAP PENYEMBUHAN LUKA : A NARRATIVE REVIEW. Jurnal Farmagazine, 9(1), 1. https://doi.org/10.47653/farm.v9i1.540\n" +
                "Kharisma, Y. (2017). Tinjauan pemanfaatan tanaman pepaya dalam kesehatan."+
        "\n\nSalam : \n" +
                "https://intip.in/PustakaSalam1\n" +
                "https://intip.in/PustakaSalam2\n" +
                "https://intip.in/PustakaSalam3\n" +
                "Harismah, K. (2017). PEMANFAATAN DAUN SALAM (Eugenia polyantha) SEBAGAI OBAT HERBAL DAN REMPAH PENYEDAP MAKANAN. Warta LPM, 19(2), 110–118. https://doi.org/10.23917/warta.v19i2.2742\n" +
                "Norihsan, M., & Megantara, S. (2018). ARTIKEL REVIEW: UJI AKTIVITAS DAN EFEK FARMAKOLOGI DAUN SALAM (Eugenia polyantha). Farmaka, 16(3), 44–54. https://jurnal.unpad.ac.id/farmaka/article/view/17319" +
        "\n\nSambiloto : \n" +
                "https://intip.in/PustakaSambiloto1\n"+
                "https://intip.in/PustakaSambiloto2\n" +
                "https://intip.in/PustakaSambiloto3\n" +
                "Priyani, R. (2020). REVIEW : MANFAAT TANAMAN SAMBILOTO (Andrographis paniculata Ness) TERHADAP SISTEM IMUN TUBUH. Jurnal Ilmu Kedokteran Dan Kesehatan, 7(3). https://doi.org/10.33024/jikk.v7i3.2963" +
        "\n\nSeledri : \n" +
                "https://intip.in/PustakaSeledri1\n" +
                "https://intip.in/PustakaSeledri2\n" +
                "https://intip.in/PustakaSeledri3\n" +
                "Naqiyya, N. (2020). Potensi Seledri (Apium Graveolens L) Sebagai Antihipertensi. Journal of Health Science and Physiotherapy, 2(2), 160–166. https://doi.org/10.35893/jhsp.v2i2.50\n" +
                "Lazdia, W., Rahma, W. A., Lubis, A. S., & Sulastri, T. (2020). PENGARUH REBUSAN DAUN SELEDRI UNTUK MENURUNKAN TEKANAN DARAH PADA PENDERITA HIPERTENSI. Empowering Society Journal, 1(1), 26–32. https://ojs.fdk.ac.id/index.php/ESJ/article/view/666/pdf"+
        "\n\nSirih : \n" +
                "https://intip.in/PustakaSirih1\n" +
                "https://intip.in/PustakaSirih2\n" +
                "Ningtias, A. F., Asyiah, I. N., & P. (2014). Manfaat Daun Sirih (Piper betle L.) Sebagai Obat Tradisional Penyakit Dalam di Kecamatan Kalianget Kabupaten Sumenep Madura. Artikel Ilmiah Hasil Penelitian Mahasiswa Tahun 2014 UNEJ, 1(1), 1–4.\n" +
                "Megasari, N. S., & Sadewo, F. X. S. (2021). Sosialisasi Pemanfaatan Daun Sirih Di Desa Sambiroto, Kec. Baron Sebagai Obah Dari Kemu. Jurnal Mandala Pengabdian Masyarakat, 2(1), 1–14. https://doi.org/10.35311/jmpm.v2i1.25"+
        "\n\nTemu Ireng : \n" +
                "https://intip.in/PustakaTemuIreng1\n" +
                "https://intip.in/PustakaTemuIreng2\n" +
                "https://intip.in/PustakaTemuIreng3\n" +
                "https://intip.in/PustakaTemuIreng4"
        return v
    }


}