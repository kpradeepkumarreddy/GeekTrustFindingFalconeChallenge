package com.geektrust.findingfalcone.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.geektrust.findingfalcone.callback_interfaces.BottomSheetItemSelectionInterface
import com.geektrust.findingfalcone.R
import com.geektrust.findingfalcone.models.PlanetsTO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.destination_selection_bottom_sheet_fragment.*

class DestinationSelectionBottomSheetFragment(private val planetList: List<PlanetsTO>?,
                                              private val bottomSheetItemSelectionInterface:
                                              BottomSheetItemSelectionInterface)
    : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var toast: Toast

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d("log", "onCreateView()")
        return inflater.inflate(R.layout.destination_selection_bottom_sheet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("log", "onActivityCreated()")
        // if planetList is null, return from here
        planetList ?: return
        Log.d("log", "onActivityCreated(), planetList is not null")

        toast = Toast.makeText(activity, "Please select a different destination, this " +
                "destination already selected", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                R.color.colorPrimary))

        tvDest1.text = planetList[0].name
        tvDest2.text = planetList[1].name
        tvDest3.text = planetList[2].name
        tvDest4.text = planetList[3].name
        tvDest5.text = planetList[4].name
        tvDest6.text = planetList[5].name
        tvDest1.setOnClickListener(this)
        tvDest2.setOnClickListener(this)
        tvDest3.setOnClickListener(this)
        tvDest4.setOnClickListener(this)
        tvDest5.setOnClickListener(this)
        tvDest6.setOnClickListener(this)

        if (planetList[0].isSelected!!) {
            tvDest1.setTextColor(resources.getColor(R.color.selectedLabelColor))
        }
        if (planetList[1].isSelected!!) {
            tvDest2.setTextColor(resources.getColor(R.color.selectedLabelColor))
        }
        if (planetList[2].isSelected!!) {
            tvDest3.setTextColor(resources.getColor(R.color.selectedLabelColor))
        }
        if (planetList[3].isSelected!!) {
            tvDest4.setTextColor(resources.getColor(R.color.selectedLabelColor))
        }
        if (planetList[4].isSelected!!) {
            tvDest5.setTextColor(resources.getColor(R.color.selectedLabelColor))
        }
        if (planetList[5].isSelected!!) {
            tvDest6.setTextColor(resources.getColor(R.color.selectedLabelColor))
        }
    }

    override fun onClick(view: View?) {
        // return from here if planetList is null
        planetList ?: return

        Log.d("log", "onclick()")
        when (view?.id) {
            R.id.tvDest1 -> {
                if (planetList[0].isSelected!!) {
                    toast.show()
                } else {
                    planetList[0].isSelected = true
                    bottomSheetItemSelectionInterface.selectedDestination(0)
                    dismiss()
                }
            }
            R.id.tvDest2 -> {
                if (planetList[1].isSelected!!) {
                    toast.show()
                } else {
                    planetList[1].isSelected = true
                    bottomSheetItemSelectionInterface.selectedDestination(1)
                    dismiss()
                }
            }
            R.id.tvDest3 -> {
                if (planetList[2].isSelected!!) {
                    toast.show()
                } else {
                    planetList[2].isSelected = true
                    bottomSheetItemSelectionInterface.selectedDestination(2)
                    dismiss()
                }
            }
            R.id.tvDest4 -> {
                if (planetList[3].isSelected!!) {
                    toast.show()
                } else {
                    planetList[3].isSelected = true
                    bottomSheetItemSelectionInterface.selectedDestination(3)
                    dismiss()
                }
            }
            R.id.tvDest5 -> {
                if (planetList[4].isSelected!!) {
                    toast.show()
                } else {
                    planetList[4].isSelected = true
                    bottomSheetItemSelectionInterface.selectedDestination(4)
                    dismiss()
                }
            }
            R.id.tvDest6 -> {
                if (planetList[5].isSelected!!) {
                    toast.show()
                } else {
                    planetList[5].isSelected = true
                    bottomSheetItemSelectionInterface.selectedDestination(5)
                    dismiss()
                }
            }
        }
    }
}