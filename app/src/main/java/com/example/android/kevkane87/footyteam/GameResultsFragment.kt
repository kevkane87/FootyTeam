package com.example.android.kevkane87.footyteam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android.kevkane87.footyteam.databinding.FragmentGameResultsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GameResultsFragment : Fragment() {

    private var _binding: FragmentGameResultsBinding? = null
    private val binding get() = _binding!!

   /* private val viewModel: GameResultsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, GameResultsViewModelFactory(activity.application))[GameResultsViewModel::class.java]
    }*/

    private val viewModel by viewModels<GameResultsViewModel> { GameResultsViewModelFactory(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGameResultsBinding.inflate(inflater, container, false)


        val adapter = GameResultsAdapter()
        binding.recyclerGameResults.adapter = adapter
        val items = viewModel.items


        // Collect from the PagingData Flow in the ViewModel, and submit it to the
        // PagingDataAdapter.
        lifecycleScope.launch {
                items.collectLatest {
                    adapter.submitData(it)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}