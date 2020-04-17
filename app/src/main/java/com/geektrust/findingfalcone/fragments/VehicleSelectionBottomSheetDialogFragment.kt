package com.geektrust.findingfalcone.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.geektrust.findingfalcone.R
import com.geektrust.findingfalcone.callback_interfaces.BottomSheetItemSelectionInterface
import com.geektrust.findingfalcone.models.VehiclesTO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_vehicle_selection_bottom_sheet_dialog.*

class VehicleSelectionBottomSheetDialogFragment(private val vehicleList: List<VehiclesTO>?,
                                                private val bottomSheetItemSelectionInterface:
                                                BottomSheetItemSelectionInterface,
                                                private val distance: Int?) : BottomSheetDialogFragment(),
        View.OnClickListener {
    private lateinit var toast: Toast
    private lateinit var toast2: Toast

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_selection_bottom_sheet_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            Log.d("log", "onActivityCreated()")

            toast = Toast.makeText(activity, "Please select a different vehicle", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                    R.color.colorPrimary))

            toast2 = Toast.makeText(activity, "This vehicle does not have the range to reach the dest planet, " +
                    "choose a different vehicle", Toast.LENGTH_SHORT)
            toast2.setGravity(Gravity.CENTER, 0, 0)
            toast2.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                    R.color.colorPrimary))

            // initing views
            initViews()
        } catch (ex: Exception) {
            Log.e("log", "Exception in onActivityCreated()", ex)
        }
    }

    private fun initViews() {
        try {
            // if vehicleList is null, return from here
            vehicleList ?: return
            tvVehicle1.text = vehicleList[0].name
            tvVehicle1Count.text = vehicleList[0].vehiclesCount.toString()

            tvVehicle2.text = vehicleList[1].name
            tvVehicle2Count.text = vehicleList[1].vehiclesCount.toString()

            tvVehicle3.text = vehicleList[2].name
            tvVehicle3Count.text = vehicleList[2].vehiclesCount.toString()

            tvVehicle4.text = vehicleList[3].name
            tvVehicle4Count.text = vehicleList[3].vehiclesCount.toString()

            if (vehicleList[0].vehiclesCount == 0 || (distance != null && (vehicleList[0].maxDistance < distance))) {
                tvVehicle1.setTextColor(resources.getColor(R.color.selectedLabelColor))
                tvVehicle1Count.setTextColor(resources.getColor(R.color.selectedLabelColor))
            }
            if (vehicleList[1].vehiclesCount == 0 || (distance != null && (vehicleList[1].maxDistance < distance))) {
                tvVehicle2.setTextColor(resources.getColor(R.color.selectedLabelColor))
                tvVehicle2Count.setTextColor(resources.getColor(R.color.selectedLabelColor))
            }
            if (vehicleList[2].vehiclesCount == 0 || (distance != null && (vehicleList[2].maxDistance < distance))) {
                tvVehicle3.setTextColor(resources.getColor(R.color.selectedLabelColor))
                tvVehicle3Count.setTextColor(resources.getColor(R.color.selectedLabelColor))
            }
            if (vehicleList[3].vehiclesCount == 0 || (distance != null && (vehicleList[3].maxDistance < distance))) {
                tvVehicle4.setTextColor(resources.getColor(R.color.selectedLabelColor))
                tvVehicle4Count.setTextColor(resources.getColor(R.color.selectedLabelColor))
            }

            llVehicle1.setOnClickListener(this)
            llVehicle2.setOnClickListener(this)
            llVehicle3.setOnClickListener(this)
            llVehicle4.setOnClickListener(this)

        } catch (ex: Exception) {
            Log.e("log", "Exception in initViews()", ex)
        }
    }

    override fun onClick(view: View?) {
        try {
            // return from here if planetList is null
            vehicleList ?: return

            when (view?.id) {
                R.id.llVehicle1 -> {
                    when {
                        vehicleList[0].vehiclesCount == 0 -> {
                            toast.show()
                        }
                        distance != null && (vehicleList[0].maxDistance < distance) -> {
                            toast2.show()
                        }
                        else -> {
                            vehicleList[0].vehiclesCount--
                            bottomSheetItemSelectionInterface.selectedVehicle(0)
                            dismiss()
                        }
                    }
                }
                R.id.llVehicle2 -> {
                    when {
                        vehicleList[1].vehiclesCount == 0 -> {
                            toast.show()
                        }
                        distance != null && (vehicleList[1].maxDistance < distance) -> {
                            toast2.show()
                        }
                        else -> {
                            vehicleList[1].vehiclesCount--
                            bottomSheetItemSelectionInterface.selectedVehicle(1)
                            dismiss()
                        }
                    }
                }
                R.id.llVehicle3 -> {
                    when {
                        vehicleList[2].vehiclesCount == 0 -> {
                            toast.show()
                        }
                        distance != null && (vehicleList[2].maxDistance < distance) -> {
                            toast2.show()
                        }
                        else -> {
                            vehicleList[2].vehiclesCount--
                            bottomSheetItemSelectionInterface.selectedVehicle(2)
                            dismiss()
                        }
                    }
                }
                R.id.llVehicle4 -> {
                    when {
                        vehicleList[3].vehiclesCount == 0 -> {
                            toast.show()
                        }
                        distance != null && (vehicleList[3].maxDistance < distance) -> {
                            toast2.show()
                        }
                        else -> {
                            vehicleList[3].vehiclesCount--
                            bottomSheetItemSelectionInterface.selectedVehicle(3)
                            dismiss()
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in onClick()", ex)
        }
    }
}