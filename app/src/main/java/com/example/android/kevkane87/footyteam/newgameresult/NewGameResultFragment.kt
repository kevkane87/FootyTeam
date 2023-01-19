package com.example.android.kevkane87.footyteam.newgameresult

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.android.kevkane87.footyteam.R
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.databinding.FragmentNewGameResultBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

    private var dateToday = LocalDate.now()
    private var gameDate = Calendar.getInstance()
    private val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.UK)
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
    private val dateFormatDatabase = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.UK)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.add_result)

        _binding = FragmentNewGameResultBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dateTextView.text = dateToday.format(dateFormat)

        binding.buttonSave.setOnClickListener {

           // val date = LocalDate.parse(date, dateFormatDatabase)
           // binding.date.text = dateFormat.format(date)

            val dateString = binding.dateTextView.text.toString().ifEmpty { "" }
            val date = LocalDate.parse(dateString, dateFormat)


            val homeTeam = binding.homeTeamInput.text.toString().ifEmpty { "" }
            val awayTeam = binding.awayTeamInput.text.toString().ifEmpty { "" }
            val homeGoals = binding.homeGoalsInput.text.toString().ifEmpty { "0" }
            val awayGoals = binding.awayGoalsInput.text.toString().ifEmpty { "0" }

            val result =  GameResult(
                0,
                dateFormatDatabase.format(date),
                homeTeam,
                awayTeam,
                homeGoals.toInt(),
                awayGoals.toInt()
            )

            viewModel.saveResult(result)

            findNavController().navigate(R.id.action_newGameResultFragment_to_homeFragment)

        }

        binding.homeCheckbox.setOnClickListener {
            setMyTeamCheckbox(binding.homeTeamInput, binding.homeCheckbox)
        }

        binding.awayCheckbox.setOnClickListener {
            setMyTeamCheckbox(binding.awayTeamInput, binding.awayCheckbox)
        }

        binding.buttonSetDate.setOnClickListener {
            setDateDialog(binding)
        }
    }

    private fun setDateDialog(binding: FragmentNewGameResultBinding) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            gameDate.set(Calendar.YEAR, year)
            gameDate.set(Calendar.MONTH, monthOfYear)
            gameDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.dateTextView.text = simpleDateFormat.format(gameDate.time)
        }
        val dpd = DatePickerDialog(
            requireContext(),
            dateSetListener,
            year,
            month,
            day
        )
        dpd.show()
    }

    private fun setMyTeamCheckbox(textView: TextView, checkBox: CheckBox){
        if (checkBox.isChecked){
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val myTeam = sharedPreferences?.getString(
                activity?.getString(R.string.my_team),
                ""
            ).toString()

            textView.text = Editable.Factory.getInstance().newEditable(myTeam)
        }
        else {
            textView.text = Editable.Factory.getInstance().newEditable("")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

