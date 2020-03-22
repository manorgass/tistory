package com.tistory.manorgass.permissiontest

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {
    private lateinit var layout: View
    private lateinit var tvLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.main_layout)
        tvLocation = findViewById(R.id.tv_location)

        findViewById<Button>(R.id.btn_get_location).setOnClickListener {
            if (isLocationPermissionGranted())
                getCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, R.string.no_permission_msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                // 필자의 실제 위치를 노출하지 않기 위해 [random] 값을 더해줬습니다.
                tvLocation.text = "위도: ${location.latitude + Random.nextInt() % 15} / 경도: ${location.longitude + Random.nextInt() % 15}"
            } else
                tvLocation.text = "위치를 알 수 없습니다."
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        val preference = getPreferences(Context.MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 거부만 한 경우 사용자에게 왜 필요한지 이유를 설명해주는게 좋다
                val snackBar = Snackbar.make(layout, R.string.suggest_permissison_grant, Snackbar.LENGTH_INDEFINITE)
                snackBar.setAction("권한승인") {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
                }
                snackBar.show()
            } else {
                if (isFirstCheck) {
                    // 처음 물었는지 여부를 저장
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    // 권한요청
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
                } else {
                    // 사용자가 권한을 거부하면서 다시 묻지않음 옵션을 선택한 경우
                    // requestPermission을 요청해도 창이 나타나지 않기 때문에 설정창으로 이동한다.
                    val snackBar = Snackbar.make(layout, R.string.suggest_permissison_grant_in_setting, Snackbar.LENGTH_INDEFINITE)
                    snackBar.setAction("확인") {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                    snackBar.show()
                }
            }
            return false
        } else {
            return true
        }
    }
}