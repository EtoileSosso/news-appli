package com.example.dmii.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dmii.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.location_fragment.*

class LocationFragment : Fragment() {
    private val permissionLocation = 1

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context ?: return
        val activity = activity ?: return
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                this.askForPermissionDialog()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    permissionLocation
                )
            }
        } else {
            this.geoLoc()
        }
    }

    private fun geoLoc() {
        mFusedLocationClient.lastLocation.addOnSuccessListener {
            location.text =
                getString(R.string.localization, it.latitude.toString(), it.longitude.toString())
        }
    }

    private fun askForPermissionDialog() {
        val context = context ?: return
        context.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.permission))
                .setTitle(getString(R.string.permission_title))
            builder.setPositiveButton(
                "OK"
            ) { _, _ ->
                Log.i("Oui", "Clicked")
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    permissionLocation
                )
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionLocation -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    geoLoc()
                }
            }
            else -> {
            }
        }
    }
}