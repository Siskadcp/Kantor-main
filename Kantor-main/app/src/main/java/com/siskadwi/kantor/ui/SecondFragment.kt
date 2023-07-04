package com.siskadwi.kantor.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.siskadwi.kantor.R
import com.siskadwi.kantor.application.OfficeApp
import com.siskadwi.kantor.databinding.FragmentSecondBinding
import com.siskadwi.kantor.model.Office

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val officeViewModel : OfficeViewModel by viewModels {
        OfficeViewModelFactory((applicationContext as OfficeApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var office:Office? = null
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
        office  = args.office
        if (office !=null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(office?.name)
            binding.addressEditText.setText(office?.address)
            binding.kategoriEditText.setText(office?.category)
        }
        val name= binding.nameEditText.text
        val address= binding.addressEditText.text
        val category=binding.kategoriEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else if (address.isEmpty()) {
                Toast.makeText(context, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else if (category.isEmpty()) {
                Toast.makeText(context, "Kategori tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else{
                if (office==null) {
                    val office = Office(0, name.toString(), address.toString(), category.toString())
                    officeViewModel.insert(office)
                }
                else{
                    val office = Office(office?.id!!,name.toString(), address.toString(),category.toString())
                    officeViewModel.update(office)
                }
                findNavController().popBackStack()
            }

        }

        binding.deleteButton.setOnClickListener {
            office?.let {  officeViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}