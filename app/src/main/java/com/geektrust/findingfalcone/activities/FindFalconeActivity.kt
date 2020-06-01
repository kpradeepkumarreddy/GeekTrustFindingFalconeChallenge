package com.geektrust.findingfalcone.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geektrust.findingfalcone.R
import com.geektrust.findingfalcone.ViewModels.FindFalconeViewModel
import com.geektrust.findingfalcone.callback_interfaces.BottomSheetItemSelectionInterface
import com.geektrust.findingfalcone.fragments.DestinationSelectionBottomSheetFragment
import com.geektrust.findingfalcone.fragments.VehicleSelectionBottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_find_falcone.*

class FindFalconeActivity : AppCompatActivity(), View.OnClickListener, BottomSheetItemSelectionInterface {
    private lateinit var falconeViewModel: FindFalconeViewModel
    private var selectedItemId: Int? = null
    private val selectDestErr = "select destination"
    private val selectvehicleErr = "select vehicle for destination"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_find_falcone)

            // getting the instance of ViewModel
            falconeViewModel = FindFalconeViewModel()

            // make api call and get planets data
            falconeViewModel.getPlanets()

            // make api call and get vehicles data
            falconeViewModel.getVehicles()

            // initing views
            initViews()

        } catch (ex: Exception) {
            Log.e("log", "Exception in onCreate()", ex)
        }
    }

    private fun initViews() {
        try {
            tietDest1.setOnClickListener(this)
            tietDest2.setOnClickListener(this)
            tietDest3.setOnClickListener(this)
            tietDest4.setOnClickListener(this)

            tietVehicleForDest1.setOnClickListener(this)
            tietVehicleForDest2.setOnClickListener(this)
            tietVehicleForDest3.setOnClickListener(this)
            tietVehicleForDest4.setOnClickListener(this)

            btnFindFalcone.setOnClickListener(this)
        } catch (ex: Exception) {
            Log.e("log", "Exception in initViews()", ex)
        }
    }

    override fun onClick(view: View?) {
        try {
            selectedItemId = view?.id
            clearErrors()
            when (view?.id) {
                R.id.tietDest1,
                R.id.tietDest2,
                R.id.tietDest3,
                R.id.tietDest4 -> {
                    Log.d("log", "planetList = ${falconeViewModel.planetList}")
                    val destSelectionBottomSheet = DestinationSelectionBottomSheetFragment(falconeViewModel.planetList,
                            this)
                    destSelectionBottomSheet.show(supportFragmentManager, destSelectionBottomSheet.tag)
                }
                R.id.tietVehicleForDest1 -> {
                    // check if destination is selected, before selecting the vehicle for destination
                    if (!checkIfDestinationIsSelected(tietDest1)) return

                    val planetTOPos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest1.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.tietVehicleForDest2 -> {
                    // check if destination is selected, before selecting the vehicle for destination
                    if (!checkIfDestinationIsSelected(tietDest2)) return

                    val planetTOPos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest2.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.tietVehicleForDest3 -> {
                    // check if destination is selected, before selecting the vehicle for destination
                    if (!checkIfDestinationIsSelected(tietDest3)) return

                    val planetTOPos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest3.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.tietVehicleForDest4 -> {
                    // check if destination is selected, before selecting the vehicle for destination
                    if (!checkIfDestinationIsSelected(tietDest4)) return

                    val planetTOPos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest4.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.btnFindFalcone -> {
                    // check if any field is not selected
                    if (!checkIfAllFieldsAreSelected()) {
                        return
                    }

                    // based on the selected destinations and vehicles, calculate and populate the time taken field
                    // time taken = (planetDest1Distance/vehicleForDest1Speed) + (planetDest2Distance/vehicleForDest2Speed)
                    // + (planetDest3Distance/vehicleForDest3Speed) + (planetDest4Distance/vehicleForDest4Speed)

                    val timeTaken = calculateTimeTaken()
                    Log.d("log", "timeTaken = $timeTaken")
                    tvTimeTakenValue.text = timeTaken.toString()

                    // set the data needed to make find falcone call
                    setDataNeededForFindFalconeCall()

                    // make find falcone call
                    falconeViewModel.findFalcone()

                    if (falconeViewModel.findFalconeResponseTO?.status.equals("success")) {
                        // take the user to Result Activity
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("TIME_TAKEN", "Time taken : $timeTaken")
                        val planetName = falconeViewModel.findFalconeResponseTO?.planetName
                        intent.putExtra("PLANET_FOUND", "Planet found : $planetName")
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)

                    } else {
                        // ask the user to try with different destinations and vehicles
                        val builder = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
                        builder.setTitle("Mission Failed")
                        builder.setCancelable(false)
                        builder.setMessage("Could not find the Queen AI Falcone, try in different planets")
                        builder.setPositiveButton("Retry") { dialogInterface, _ -> dialogInterface?.dismiss() }
                        builder.show()
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in onClick()", ex)
        }
    }

    private fun checkIfDestinationIsSelected(tietDest: TextInputEditText): Boolean {
        try {
            if (tietDest.text.toString().isEmpty()) {
                val toast = Toast.makeText(this, "Select the destination before choosing vehicle for " +
                        "destination", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in checkIfDestinationIsSelected()", ex)
            return false
        }
        return true
    }

    private fun checkIfAllFieldsAreSelected(): Boolean {
        try {
            // show error and toast if Dest1 is not selected
            if (tietDest1.text.toString().isEmpty()) {
                tietDest1.error = selectDestErr
                val toast = Toast.makeText(this, selectDestErr + "1", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
            // show error and toast if VehicleForDest1 is not selected
            if (tietVehicleForDest1.text.toString().isEmpty()) {
                tietVehicleForDest1.error = selectvehicleErr
                val toast = Toast.makeText(this, selectvehicleErr + "1", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
            // show error and toast if Dest2 is not selected
            if (tietDest2.text.toString().isEmpty()) {
                tietDest2.error = selectDestErr
                val toast = Toast.makeText(this, selectDestErr + "2", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
            // show error and toast if VehicleForDest2 is not selected
            if (tietVehicleForDest2.text.toString().isEmpty()) {
                tietVehicleForDest2.error = selectvehicleErr
                val toast = Toast.makeText(this, selectvehicleErr + "2", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
            // show error and toast if Dest3 is not selected
            if (tietDest3.text.toString().isEmpty()) {
                tietDest3.error = selectDestErr
                val toast = Toast.makeText(this, selectDestErr + "3", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
            // show error and toast if VehicleForDest3 is not selected
            if (tietVehicleForDest3.text.toString().isEmpty()) {
                tietVehicleForDest3.error = selectvehicleErr
                val toast = Toast.makeText(this, selectvehicleErr + "3", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
            // show error and toast if Dest4 is not selected
            if (tietDest4.text.toString().isEmpty()) {
                tietDest4.error = selectDestErr
                val toast = Toast.makeText(this, selectDestErr + "4", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
            // show error and toast if VehicleForDest4 is not selected
            if (tietVehicleForDest4.text.toString().isEmpty()) {
                tietVehicleForDest4.error = selectvehicleErr
                val toast = Toast.makeText(this, selectvehicleErr + "4", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                        R.color.colorPrimary))
                toast.show()
                return false
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in checkIfAllFieldsAreSelected()", ex)
            return false
        }
        return true
    }

    private fun calculateTimeTaken(): Int {
        // calculating the total time taken for all planets
        return timeTakenForPlanet1() + timeTakenForPlanet2() + timeTakenForPlanet3() + timeTakenForPlanet4()
    }

    private fun timeTakenForPlanet1(): Int {
        try {
            if (tietDest1.text.toString().isNotEmpty() && tietVehicleForDest1.text.toString().isNotEmpty()) {
                val planet1Pos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest1.text.toString())
                val vehicle1Pos = falconeViewModel.getVehicleIndexOfSelectedVehicle(tietVehicleForDest1.text.toString())

                val t1 = falconeViewModel.planetList?.get(planet1Pos)?.distance?.div(
                        falconeViewModel.vehicleList?.get(vehicle1Pos)?.speed!!)!!
                Log.d("log", "t1= $t1")
                return t1
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in timeTakenForPlanet1()", ex)
        }
        return 0
    }

    private fun timeTakenForPlanet2(): Int {
        try {
            if (tietDest2.text.toString().isNotEmpty() && tietVehicleForDest2.text.toString().isNotEmpty()) {
                val planet2Pos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest2.text.toString())
                val vehicle2Pos = falconeViewModel.getVehicleIndexOfSelectedVehicle(tietVehicleForDest2.text.toString())

                val t2 = falconeViewModel.planetList?.get(planet2Pos)?.distance?.div(
                        falconeViewModel.vehicleList?.get(vehicle2Pos)?.speed!!)!!
                Log.d("log", "t2= $t2")
                return t2
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in timeTakenForPlanet2()", ex)
        }
        return 0
    }

    private fun timeTakenForPlanet3(): Int {
        try {
            if (tietDest3.text.toString().isNotEmpty() && tietVehicleForDest3.text.toString().isNotEmpty()) {
                val planet3Pos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest3.text.toString())
                val vehicle3Pos = falconeViewModel.getVehicleIndexOfSelectedVehicle(tietVehicleForDest3.text.toString())
                val t3 = falconeViewModel.planetList?.get(planet3Pos)?.distance?.div(
                        falconeViewModel.vehicleList?.get(vehicle3Pos)?.speed!!)!!
                Log.d("log", "t3= $t3")
                return t3
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in timeTakenForPlanet3()", ex)
        }
        return 0
    }

    private fun timeTakenForPlanet4(): Int {
        try {
            if (tietDest4.text.toString().isNotEmpty() && tietVehicleForDest4.text.toString().isNotEmpty()) {
                val planet4Pos = falconeViewModel.getPlanetIndexOfSelectedDest(tietDest4.text.toString())
                val vehicle4Pos = falconeViewModel.getVehicleIndexOfSelectedVehicle(tietVehicleForDest4.text.toString())
                val t4 = falconeViewModel.planetList?.get(planet4Pos)?.distance?.div(
                        falconeViewModel.vehicleList?.get(vehicle4Pos)?.speed!!)!!
                Log.d("log", "t4= $t4")
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in timeTakenForPlanet4()", ex)
        }
        return 0
    }

    private fun setDataNeededForFindFalconeCall() {
        try {
            falconeViewModel.planetNames.clear()
            falconeViewModel.vehicleNames.clear()

            // setting the selected planet names to make the FindFalcone API call
            falconeViewModel.planetNames.add(tietDest1.text.toString())
            falconeViewModel.planetNames.add(tietDest2.text.toString())
            falconeViewModel.planetNames.add(tietDest3.text.toString())
            falconeViewModel.planetNames.add(tietDest4.text.toString())

            // setting the selected vehicle names to make the FindFalcone API call
            falconeViewModel.vehicleNames.add(tietVehicleForDest1.text.toString())
            falconeViewModel.vehicleNames.add(tietVehicleForDest2.text.toString())
            falconeViewModel.vehicleNames.add(tietVehicleForDest3.text.toString())
            falconeViewModel.vehicleNames.add(tietVehicleForDest4.text.toString())
        } catch (ex: Exception) {
            Log.e("log", "Exception in setDataNeededForFindFalconeCall()", ex)
        }
    }

    private fun clearErrors() {
        try {
            tietDest1.error = null
            tietDest2.error = null
            tietDest3.error = null
            tietDest4.error = null

            tietVehicleForDest1.error = null
            tietVehicleForDest2.error = null
            tietVehicleForDest3.error = null
            tietVehicleForDest4.error = null
        } catch (ex: Exception) {
            Log.e("log", "Exception in clearErrors()", ex)
        }
    }

    override fun selectedDestination(pos: Int) {
        try {
            when (selectedItemId) {
                R.id.tietDest1 -> {
                    val prevSelectedDestination = tietDest1.text.toString()
                    Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                    // to unselect the previous selected item from the bottom sheet
                    falconeViewModel.unselectPrevSelectedDest(prevSelectedDestination)
                    tietDest1.setText(falconeViewModel.planetList?.get(pos)?.name)
                    selectedItemId = R.id.tietVehicleForDest1
                    selectedVehicle(-1)
                }
                R.id.tietDest2 -> {
                    val prevSelectedDestination = tietDest2.text.toString()
                    Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                    // to unselect the previous selected item from the bottom sheet
                    falconeViewModel.unselectPrevSelectedDest(prevSelectedDestination)
                    tietDest2.setText(falconeViewModel.planetList?.get(pos)?.name)
                    selectedItemId = R.id.tietVehicleForDest2
                    selectedVehicle(-1)
                }
                R.id.tietDest3 -> {
                    val prevSelectedDestination = tietDest3.text.toString()
                    Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                    // to unselect the previous selected item from the bottom sheet
                    falconeViewModel.unselectPrevSelectedDest(prevSelectedDestination)
                    tietDest3.setText(falconeViewModel.planetList?.get(pos)?.name)
                    selectedItemId = R.id.tietVehicleForDest3
                    selectedVehicle(-1)
                }
                R.id.tietDest4 -> {
                    val prevSelectedDestination = tietDest4.text.toString()
                    Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                    // to unselect the previous selected item from the bottom sheet
                    falconeViewModel.unselectPrevSelectedDest(prevSelectedDestination)
                    tietDest4.setText(falconeViewModel.planetList?.get(pos)?.name)
                    selectedItemId = R.id.tietVehicleForDest4
                    selectedVehicle(-1)
                }
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in selectedDestination()", ex)
        }
    }

    override fun selectedVehicle(pos: Int) {
        try {
            when (selectedItemId) {
                R.id.tietVehicleForDest1 -> {
                    val prevSelectedVehicle = tietVehicleForDest1.text.toString()
                    Log.d("log", "prevSelectedVehicle = $prevSelectedVehicle")
                    falconeViewModel.unselectPrevSelectedVehicle(prevSelectedVehicle)
                    if (pos == -1) {
                        tietVehicleForDest1.setText("")
                    } else {
                        tietVehicleForDest1.setText(falconeViewModel.vehicleList?.get(pos)?.name)
                    }
                    val timeTaken = calculateTimeTaken()
                    Log.d("log", "timeTaken = $timeTaken")
                    tvTimeTakenValue.text = timeTaken.toString()
                }
                R.id.tietVehicleForDest2 -> {
                    val prevSelectedVehicle = tietVehicleForDest2.text.toString()
                    Log.d("log", "prevSelectedVehicle = $prevSelectedVehicle")
                    falconeViewModel.unselectPrevSelectedVehicle(prevSelectedVehicle)
                    if (pos == -1) {
                        tietVehicleForDest2.setText("")
                    } else {
                        tietVehicleForDest2.setText(falconeViewModel.vehicleList?.get(pos)?.name)
                    }
                    val timeTaken = calculateTimeTaken()
                    Log.d("log", "timeTaken = $timeTaken")
                    tvTimeTakenValue.text = timeTaken.toString()
                }
                R.id.tietVehicleForDest3 -> {
                    val prevSelectedVehicle = tietVehicleForDest3.text.toString()
                    Log.d("log", "prevSelectedVehicle = $prevSelectedVehicle")
                    falconeViewModel.unselectPrevSelectedVehicle(prevSelectedVehicle)
                    if (pos == -1) {
                        tietVehicleForDest3.setText("")
                    } else {
                        tietVehicleForDest3.setText(falconeViewModel.vehicleList?.get(pos)?.name)
                    }
                    val timeTaken = calculateTimeTaken()
                    Log.d("log", "timeTaken = $timeTaken")
                    tvTimeTakenValue.text = timeTaken.toString()
                }
                R.id.tietVehicleForDest4 -> {
                    val prevSelectedVehicle = tietVehicleForDest4.text.toString()
                    Log.d("log", "prevSelectedVehicle = $prevSelectedVehicle")
                    falconeViewModel.unselectPrevSelectedVehicle(prevSelectedVehicle)
                    if (pos == -1) {
                        tietVehicleForDest4.setText("")
                    } else {
                        tietVehicleForDest4.setText(falconeViewModel.vehicleList?.get(pos)?.name)
                    }
                    val timeTaken = calculateTimeTaken()
                    Log.d("log", "timeTaken = $timeTaken")
                    tvTimeTakenValue.text = timeTaken.toString()
                }
            }
        } catch (ex: Exception) {
            Log.e("log", "Exception in selectedVehicle()", ex)
        }
    }
}