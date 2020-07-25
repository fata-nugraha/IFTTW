# IF3210-2020-19-IFTTT

## Deskripsi Aplikasi
Aplikasi dapat menjalankan 2 buah modul kondisi
<ol>
   <li>
   Modul Date Timer
   
   Pengguna dapat menetapkan waktu yang bisa di-pick pada **Fragment** Date dan Time.
   </li>

   <li>
   Modul Sensor
   
   Pengguna dapat menggunakan sensor untuk sebagai triggerer. Untuk tugas ini, sensor yang digunakan ialah **accelerometer**
   </li>
</ol>
Aplikasi juga dapat meng-eksekusi 3 buah modul aksi
<ol>
   <li>
   Modul API
   </li>
   
   <li>
   Modul Notification
   
   Pengguna mendapatkan notifikasi pada panel notification dengan **Title** dan **Text** yang diinginkan
   </li>

   <li>
   Modul WiFi
   
   Service akan menyalakan atau mematikan **WiFi** sesuai dengan modul kondisi yang ditentukan
   </li>
</ol>

## Cara Kerja Aplikasi dan Modulnya

Untuk mendukung modularitas agar modul bisa digunakan untuk modul lainnya, maka kami membuat kelas untuk **Action** dan **Condition**. Pemilihan Modul dapat digunakan dengan **selecting** dari **Spinner**.

#### Modul Condition

<ul>
   <li>
   Modul Condition Accelerometer
   
   akan mengaktifkan modul aksi ketika jumlah dari acceleration x, y, z > 30. Semua aksi dipanggil dengan **Intent** eksplisit ke kelas yang bersangkutan
   </li>
   
   <li>
   Modul Condition Alarm
   
   Desain component dengan 2 button yang akan meng-*inflate* **time** dan **date** picker. Secara default jika button tidak ditekan, maka datetime diset ke *now()*. Datetime disimpan dalam tipe **LocalDateTime**
   </li>
   
</ul>

#### Modul Aksi

<ul>
   <li>
   Modul Aksi API
   
   Menembak API **GET** *request* ke endpoint web.
   </li>
   
   <li>
   Modul Aksi Notification
   
   Akan muncul / *popup* pada panel **Notification** dengan *title* dan *text* yang di-*customize*. Kelas notifikasi berupa **NotificationHelper**.
   </li>

   <li>
   Modul Aksi WiFi
   
   Dijalankan dengan men-*set* **setWifiEnabled(*boolean*)** dengan **wifiManager**. Sebelumnya pada kelas **Condition** sudah diset apakah wifi diset on/off ketika modul kondisi terpenuhi.
   </li>
   
</ul>

## Library dan Justifikasi Penggunaannya

<ul>
   <li>
   AlarmManager
   
   Menyalakan *service intent* untuk reminder
   </li>
   
   <li>
   BroadcastReceiver
   
   Untuk mengabari semua app bahwa WiFi berhasil di-set
   </li>
   
   <li>
   DateTime Picker
   
   Mengambil input berupa *date* dan *time* dalam **fragment**
   </li>
   
   <li>
   LocalDateTime
   
   Kelas untuk menyimpan data **date** dan **time**
   </li>
   
   <li>
   SensorManager dan SensorEvent
   
   Untuk mendapatkan jenis sensor dan *value* yang dibaca sensor
   </li>
   
   <li>
   Spinner
   
   **dropdown** menu, pada pemilihan modul kondisi dan aksi
   </li>
   
   <li>
   Toast
   
   Memunculkan *untouchable* text 
   </li>
   
   <li>
   Volley
   
   Untuk melakukan API *request*
   </li>
   
   <li>
   wifiManager
   
   Set WiFi *on*/*off*
   </li>
</ul>

## Screenshot Aplikasi

Screenshot aplikasi terdapat pada **Gitlab WIKI**
