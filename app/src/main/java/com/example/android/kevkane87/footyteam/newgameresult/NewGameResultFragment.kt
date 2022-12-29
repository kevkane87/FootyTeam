package com.example.android.kevkane87.footyteam.newgameresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.kevkane87.footyteam.R
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.databinding.FragmentNewGameResultBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NewGameResultFragment : Fragment() {

    private var _binding: FragmentNewGameResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: NewGameResultViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, NewGameResultViewModelFactory(activity.application))[NewGameResultViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewGameResultBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {

            val date = binding.dateInput.text.toString().ifEmpty { "" }
            val homeTeam = binding.homeTeamInput.text.toString().ifEmpty { "" }
            val awayTeam = binding.awayTeamInput.text.toString().ifEmpty { "" }
            val homeGoals = binding.homeGoalsInput.text.toString().ifEmpty { "0" }
            val awayGoals = binding.awayGoalsInput.text.toString().ifEmpty { "0" }

            val result =  GameResult(
                0,
                date,
                homeTeam,
                awayTeam,
                homeGoals.toInt(),
                awayGoals.toInt()
            )

            viewModel.saveResult(result)

            findNavController().navigate(R.id.action_newGameResultFragment_to_homeFragment)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}