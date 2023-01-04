package com.example.android.kevkane87.footyteam.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.android.kevkane87.footyteam.R
import com.example.android.kevkane87.footyteam.databinding.FragmentStatsBinding
import kotlinx.coroutines.delay


class StatsFragment: Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this,
            StatsViewModelFactory(activity.application))[StatsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentStatsBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.stats)


        binding.radioGroupSelect.check(R.id.all_games)

        viewModel.allResults.observe(viewLifecycleOwner) {
            calculateStats(binding)
        }

        binding.allGames.setOnClickListener{
            calculateStats(binding)
        }

        binding.homeGames.setOnClickListener{
            calculateStats(binding)
        }

        binding.awayGames.setOnClickListener{
            calculateStats(binding)
        }


        return binding.root
    }


   private fun calculateStats(binding: FragmentStatsBinding){

       val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
       viewModel.myTeam = sharedPreferences?.getString(
           activity?.getString(R.string.my_team),
           ""
       ).toString()

       binding.myTeam.text = viewModel.myTeam

       Log.d("StatsViewModel", viewModel.size.toString())

       when (binding.radioGroupSelect.checkedRadioButtonId){
           R.id.all_games -> viewModel.calculateStats("All")
           R.id.home_games -> viewModel.calculateStats("Home")
           R.id.away_games -> viewModel.calculateStats("Away")
       }

       if (viewModel.games > 0) {

           binding.noOfGames.text = viewModel.games.toString()
           binding.noOfWins.text = viewModel.wins.toString()
           binding.noOfDraws.text = viewModel.draws.toString()
           binding.noOfLosses.text = viewModel.losses.toString()
           binding.winPercent.text = viewModel.winPercentage.toString()
           binding.drawPercent.text = viewModel.drawPercentage.toString()
           binding.lossPercent.text = viewModel.lossPercentage.toString()
           binding.form.text = viewModel.form
           binding.totGoalsFor.text = viewModel.goalsFor.toString()
           binding.totGoalsAgainst.text = viewModel.goalsAgainst.toString()
           binding.avGoalsFor.text = viewModel.avGoalsFor.toString()
           binding.avGoalsAgainst.text = viewModel.avGoalsAgainst.toString()
           binding.biggestWin.text = viewModel.biggestWin
           binding.biggestLoss.text = viewModel.biggestLoss
       }
   }
}


