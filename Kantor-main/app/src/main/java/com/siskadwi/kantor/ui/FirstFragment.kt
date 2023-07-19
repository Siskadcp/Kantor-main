package com.siskadwi.kantor.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.siskadwi.kantor.R
import com.siskadwi.kantor.application.OfficeApp
import com.siskadwi.kantor.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val officeViewModel : OfficeViewModel by viewModels {
        OfficeViewModelFactory((applicationContext as OfficeApp).repository)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OfficeListAdapter{office ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(office)
            findNavController().navigate(action)
        }
        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        officeViewModel.allOffice.observe(viewLifecycleOwner){ office ->
            office.let {
                if (office.isEmpty()) {
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.illustrationImageView.visibility = View.VISIBLE
                }else{
                    binding.emptyTextView.visibility = View.GONE
                    binding.illustrationImageView.visibility = View.GONE
                }
                adapter.submitList(office)
            }
        }
       binding.addFAB.setOnClickListener {
           val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
           findNavController().navigate(action)
        }
        binding.aboutFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AboutFragment)
        }
        binding.katalogFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_CatalogFragment)
        }
        binding.contactFAB.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ContactFragment)
        }
     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}