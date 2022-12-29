package com.example.android.kevkane87.footyteam.gameresults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

        initSwipeToDelete()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            // enable the items to swipe to the left or right
           /* override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val gameResultsViewHolder = viewHolder as GameResultsViewHolder
                return if (gameResultsViewHolder.itemId != null) {
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
                } else {
                    makeMovementFlags(0, 0)
                }
            }*/

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {



                (viewHolder as GameResultsViewHolder).itemId.let {
                    viewModel.deleteGame(it)
                }


            }
        }).attachToRecyclerView(binding.recyclerGameResults)
    }
}