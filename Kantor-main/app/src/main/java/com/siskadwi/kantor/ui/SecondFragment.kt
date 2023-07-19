package com.siskadwi.kantor.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.siskadwi.kantor.R
import com.siskadwi.kantor.application.OfficeApp
import com.siskadwi.kantor.databinding.FragmentSecondBinding
import com.siskadwi.kantor.model.Office

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val officeViewModel: OfficeViewModel by viewModels {
        OfficeViewModelFactory((applicationContext as OfficeApp).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var office: Office? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        office = args.office
        if (office != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(office?.name)
            binding.addressEditText.setText(office?.address)
            binding.kategoriEditText.setText(office?.category)
        }
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val category = binding.kategoriEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (category.isEmpty()) {
                Toast.makeText(context, "Kategori tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (office == null) {
                    val office = Office(
                        0,
                        name.toString(),
                        address.toString(),
                        category.toString(),
                        currentLatLang?.latitude,
                        currentLatLang?.longitude
                    )
                    officeViewModel.insert(office)
                } else {
                    val office = Office(
                        office?.id!!,
                        name.toString(),
                        address.toString(),
                        category.toString(),
                        currentLatLang?.latitude,
                        currentLatLang?.longitude
                    )
                    officeViewModel.update(office)
                }
                findNavController().popBackStack()
            }

        }

        binding.deleteButton.setOnClickListener {
            office?.let { officeViewModel.delete(it) }
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        val sydney = LatLng(-34.0 , 151.0)
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {

    }
    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext, "akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null){
                    var latlang =LatLng(location.latitude,location.longitude)
                    currentLatLang = latlang
                    var title = "Marker"

                    if (office!=null){
                        title=office?.name.toString()
                        val newCurrentLocation = LatLng(office?.latitude!!,office?.longitude!!)
                        latlang=newCurrentLocation
                    }

                    val markerOption = MarkerOptions()
                        .position(latlang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_kantor))
                    mMap.addMarker(markerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang,15f))
                }
            }
    }
}