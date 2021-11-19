package com.example.room_lib_sample.director

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.room_lib_sample.R
import com.example.room_lib_sample.db.Director
import com.example.room_lib_sample.db.MoviesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DirectorSaveDialogFragment : DialogFragment() {

    private var directorFullNameExtra: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        directorFullNameExtra = arguments!!.getString(EXTRA_DIRECTOR_FULL_NAME)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_director, null)
        val directorEditText = view.findViewById<EditText>(R.id.etDirectorFullName)
        val deleteDirectorButton = view.findViewById<Button>(R.id.btnDirectorDelete)
        val cancelDialogButton = view.findViewById<Button>(R.id.btnCancelDialogDirector)
        val saveDirectorButton = view.findViewById<Button>(R.id.btnDirectorSave)
        directorEditText.setText(directorFullNameExtra)
        directorEditText.setSelection(directorFullNameExtra?.length ?: 0)

        deleteDirectorButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                deleteDirector(directorEditText.text.toString()) }
            dialog?.cancel()
        }
        cancelDialogButton.setOnClickListener { dialog?.cancel() }
        saveDirectorButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                saveDirector(directorEditText.text.toString()) }
            dialog?.cancel()
        }
        alertDialogBuilder.setView(view).setTitle(getString(R.string.dialog_movie_title))

        return alertDialogBuilder.create()
    }

    private suspend fun deleteDirector(fullName: String) {
        if (TextUtils.isEmpty(fullName)) {
            return
        }
        val directorDao = MoviesDatabase.getDatabase(requireContext()).directorDao()

        if (directorFullNameExtra != null) {
            // clicked on item row -> update
            val directorToUpdate = directorDao.findDirectorByName(fullName)
            if (directorToUpdate != null) {
                directorDao.delete(directorToUpdate)
            }
        }
    }

    private suspend fun saveDirector(fullName: String) {
        if (TextUtils.isEmpty(fullName)) {
            return
        }
        val directorDao = MoviesDatabase.getDatabase(requireContext()).directorDao()
        if (directorFullNameExtra != null) {
            // clicked on item row -> update
            val directorToUpdate = directorDao.findDirectorByName(directorFullNameExtra)
            if (directorToUpdate != null) {
                if (directorToUpdate.fullName != fullName) {
                    directorToUpdate.fullName = fullName
                    directorDao.update(directorToUpdate)
                }
            }
        } else {
            directorDao.insert(Director(fullName = fullName))
        }
    }

    companion object {
        private const val EXTRA_DIRECTOR_FULL_NAME = "director_full_name"
        const val TAG_DIALOG_DIRECTOR_SAVE = "dialog_director_save"

        fun newInstance(directorFullName: String?): DirectorSaveDialogFragment {
            val fragment = DirectorSaveDialogFragment()
            val args = Bundle()
            args.putString(EXTRA_DIRECTOR_FULL_NAME, directorFullName)
            fragment.arguments = args
            return fragment
        }
    }
}