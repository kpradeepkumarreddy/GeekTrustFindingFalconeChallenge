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
import com.geektrust.findingfalcone.callback_interfaces.BottomSheetItemSelectionInterface
import com.geektrust.findingfalcone.R
import com.geektrust.findingfalcone.ViewModels.FindFalconeViewModel
import com.geektrust.findingfalcone.fragments.DestinationSelectionBottomSheetFragment
import com.geektrust.findingfalcone.fragments.VehicleSelectionBottomSheetDialogFragment
import com.geektrust.findingfalcone.models.PlanetsTO
import com.geektrust.findingfalcone.models.VehiclesTO
import kotlinx.android.synthetic.main.activity_find_falcone.*

class FindFalconeActivity : AppCompatActivity(), View.OnClickListener, BottomSheetItemSelectionInterface {
    private lateinit var falconeViewModel: FindFalconeViewModel
    private var selectedItemId: Int? = null
    private val selectDestErr = "select destination"
    private val selectvehicleErr = "select vehicle for destination"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_falcone)
        falconeViewModel = FindFalconeViewModel()
        falconeViewModel.getPlanets()
        falconeViewModel.getVehicles()

        tietDest1.setOnClickListener(this)
        tietDest2.setOnClickListener(this)
        tietDest3.setOnClickListener(this)
        tietDest4.setOnClickListener(this)

        tietVehicleForDest1.setOnClickListener(this)
        tietVehicleForDest2.setOnClickListener(this)
        tietVehicleForDest3.setOnClickListener(this)
        tietVehicleForDest4.setOnClickListener(this)

        btnFindFalcone.setOnClickListener(this)
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
                    if (tietDest1.text.toString().isEmpty()) {
                        val toast = Toast.makeText(this, "Select the destination before choosing vehicle for " +
                                "destination", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    val planetTOPos = getPlanetIndexOfSelectedDest(tietDest1.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != null && planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.tietVehicleForDest2 -> {
                    if (tietDest2.text.toString().isEmpty()) {
                        val toast = Toast.makeText(this, "Select the destination before choosing vehicle for " +
                                "destination", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    val planetTOPos = getPlanetIndexOfSelectedDest(tietDest2.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != null && planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.tietVehicleForDest3 -> {
                    if (tietDest3.text.toString().isEmpty()) {
                        val toast = Toast.makeText(this, "Select the destination before choosing vehicle for " +
                                "destination", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    val planetTOPos = getPlanetIndexOfSelectedDest(tietDest3.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != null && planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.tietVehicleForDest4 -> {
                    if (tietDest4.text.toString().isEmpty()) {
                        val toast = Toast.makeText(this, "Select the destination before choosing vehicle for " +
                                "destination", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    val planetTOPos = getPlanetIndexOfSelectedDest(tietDest4.text.toString())
                    var planetDistance: Int? = null
                    if (planetTOPos != null && planetTOPos != -1) {
                        planetDistance = falconeViewModel.planetList?.get(planetTOPos)?.distance
                    }
                    val vehicleSelectionBottomSheet = VehicleSelectionBottomSheetDialogFragment(
                            falconeViewModel.vehicleList, this, planetDistance)
                    vehicleSelectionBottomSheet.show(supportFragmentManager, vehicleSelectionBottomSheet.tag)
                }
                R.id.btnFindFalcone -> {
                    // check if any field is not selected
                    if (tietDest1.text.toString().isEmpty()) {
                        tietDest1.error = selectDestErr
                        val toast = Toast.makeText(this, selectDestErr + "1", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    if (tietVehicleForDest1.text.toString().isEmpty()) {
                        tietVehicleForDest1.error = selectvehicleErr
                        val toast = Toast.makeText(this, selectvehicleErr + "1", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    if (tietDest2.text.toString().isEmpty()) {
                        tietDest2.error = selectDestErr
                        val toast = Toast.makeText(this, selectDestErr + "2", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    if (tietVehicleForDest2.text.toString().isEmpty()) {
                        tietVehicleForDest2.error = selectvehicleErr
                        val toast = Toast.makeText(this, selectvehicleErr + "2", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    if (tietDest3.text.toString().isEmpty()) {
                        tietDest3.error = selectDestErr
                        val toast = Toast.makeText(this, selectDestErr + "3", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    if (tietVehicleForDest3.text.toString().isEmpty()) {
                        tietVehicleForDest3.error = selectvehicleErr
                        val toast = Toast.makeText(this, selectvehicleErr + "3", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    if (tietDest4.text.toString().isEmpty()) {
                        tietDest4.error = selectDestErr
                        val toast = Toast.makeText(this, selectDestErr + "4", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    if (tietVehicleForDest4.text.toString().isEmpty()) {
                        tietVehicleForDest4.error = selectvehicleErr
                        val toast = Toast.makeText(this, selectvehicleErr + "4", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.view.findViewById<TextView>(android.R.id.message).setTextColor(resources.getColor(
                                R.color.colorPrimary))
                        toast.show()
                        return
                    }
                    // based on the selected destinations and vehicles, calculate and populate the time taken field
                    // time taken = (planetDest1Distance/vehicleForDest1Speed) + (planetDest2Distance/vehicleForDest2Speed)
                    // + (planetDest3Distance/vehicleForDest3Speed) + (planetDest4Distance/vehicleForDest4Speed)

                    val timeTaken = calculateTimeTaken()
                    Log.d("log", "timeTaken = $timeTaken")
                    tvTimeTakenValue.text = timeTaken.toString()

                    falconeViewModel.planetNames.clear()
                    falconeViewModel.vehicleNames.clear()

                    falconeViewModel.planetNames.add(tietDest1.text.toString())
                    falconeViewModel.planetNames.add(tietDest2.text.toString())
                    falconeViewModel.planetNames.add(tietDest3.text.toString())
                    falconeViewModel.planetNames.add(tietDest4.text.toString())

                    falconeViewModel.vehicleNames.add(tietVehicleForDest1.text.toString())
                    falconeViewModel.vehicleNames.add(tietVehicleForDest2.text.toString())
                    falconeViewModel.vehicleNames.add(tietVehicleForDest3.text.toString())
                    falconeViewModel.vehicleNames.add(tietVehicleForDest4.text.toString())

                    // make find falcone call
                    falconeViewModel.findFalcone()


                    if (falconeViewModel.findFalconeResponseTO?.status.equals("success")) {
                        // take the user to result activity
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
            Log.e("log", "Exception in onClick()")
        }
    }

    private fun calculateTimeTaken(): Int {
        var timeTaken = 0
        try {
            var t1 = 0
            if (tietDest1.text.toString().isNotEmpty() && tietVehicleForDest1.text.toString().isNotEmpty()) {
                val planet1Pos = getPlanetIndexOfSelectedDest(tietDest1.text.toString())
                val vehicle1Pos = getVehicleIndexOfSelectedVehicle(tietVehicleForDest1.text.toString())

                t1 = falconeViewModel.planetList?.get(planet1Pos)?.distance?.div(falconeViewModel.vehicleList?.get
                (vehicle1Pos)?.speed!!)!!
                Log.d("log", "t1= $t1")
            }

            var t2 = 0
            if (tietDest2.text.toString().isNotEmpty() && tietVehicleForDest2.text.toString().isNotEmpty()) {
                val planet2Pos = getPlanetIndexOfSelectedDest(tietDest2.text.toString())
                val vehicle2Pos = getVehicleIndexOfSelectedVehicle(tietVehicleForDest2.text.toString())

                t2 = falconeViewModel.planetList?.get(planet2Pos)?.distance?.div(falconeViewModel.vehicleList?.get
                (vehicle2Pos)?.speed!!)!!
                Log.d("log", "t2= $t2")
            }

            var t3 = 0
            if (tietDest3.text.toString().isNotEmpty() && tietVehicleForDest3.text.toString().isNotEmpty()) {
                val planet3Pos = getPlanetIndexOfSelectedDest(tietDest3.text.toString())
                val vehicle3Pos = getVehicleIndexOfSelectedVehicle(tietVehicleForDest3.text.toString())
                t3 = falconeViewModel.planetList?.get(planet3Pos)?.distance?.div(falconeViewModel.vehicleList?.get
                (vehicle3Pos)?.speed!!)!!
                Log.d("log", "t3= $t3")
            }

            var t4 = 0
            if (tietDest4.text.toString().isNotEmpty() && tietVehicleForDest4.text.toString().isNotEmpty()) {
                val planet4Pos = getPlanetIndexOfSelectedDest(tietDest4.text.toString())
                val vehicle4Pos = getVehicleIndexOfSelectedVehicle(tietVehicleForDest4.text.toString())
                t4 = falconeViewModel.planetList?.get(planet4Pos)?.distance?.div(falconeViewModel.vehicleList?.get
                (vehicle4Pos)?.speed!!)!!
                Log.d("log", "t4= $t4")
            }

            timeTaken = t1.plus(t2).plus(t3).plus(t4)

        } catch (ex: Exception) {
            Log.e("log", "Exception in calculateTimeTaken()")
        }
        return timeTaken
    }

    private fun clearErrors() {
        tietDest1.error = null
        tietDest2.error = null
        tietDest3.error = null
        tietDest4.error = null

        tietVehicleForDest1.error = null
        tietVehicleForDest2.error = null
        tietVehicleForDest3.error = null
        tietVehicleForDest4.error = null
    }

    override fun selectedDestination(pos: Int) {
        when (selectedItemId) {
            R.id.tietDest1 -> {
                val prevSelectedDestination = tietDest1.text.toString()
                Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                // to unselect the previous selected item from the bottom sheet
                unselectPrevSelectedDest(prevSelectedDestination)
                tietDest1.setText(falconeViewModel.planetList?.get(pos)?.name)
                selectedItemId = R.id.tietVehicleForDest1
                selectedVehicle(-1)
            }
            R.id.tietDest2 -> {
                val prevSelectedDestination = tietDest2.text.toString()
                Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                // to unselect the previous selected item from the bottom sheet
                unselectPrevSelectedDest(prevSelectedDestination)
                tietDest2.setText(falconeViewModel.planetList?.get(pos)?.name)
                selectedItemId = R.id.tietVehicleForDest2
                selectedVehicle(-1)
            }
            R.id.tietDest3 -> {
                val prevSelectedDestination = tietDest3.text.toString()
                Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                // to unselect the previous selected item from the bottom sheet
                unselectPrevSelectedDest(prevSelectedDestination)
                tietDest3.setText(falconeViewModel.planetList?.get(pos)?.name)
                selectedItemId = R.id.tietVehicleForDest3
                selectedVehicle(-1)
            }
            R.id.tietDest4 -> {
                val prevSelectedDestination = tietDest4.text.toString()
                Log.d("log", "prevSelectedDestination = $prevSelectedDestination")
                // to unselect the previous selected item from the bottom sheet
                unselectPrevSelectedDest(prevSelectedDestination)
                tietDest4.setText(falconeViewModel.planetList?.get(pos)?.name)
                selectedItemId = R.id.tietVehicleForDest4
                selectedVehicle(-1)
            }
        }
    }

    private fun getPlanetIndexOfSelectedDest(curSelectedDestination: String): Int {
        var prevItemPos: Int = -1
        if (curSelectedDestination != null && curSelectedDestination.isNotEmpty()) {
            val dummyPlanetTO = PlanetsTO(curSelectedDestination, null, null)
            prevItemPos = falconeViewModel.planetList?.indexOf(dummyPlanetTO)!!
            Log.d("log", "prevItemPos = $prevItemPos")
        }
        return prevItemPos
    }

    private fun unselectPrevSelectedDest(curSelectedDestination: String) {
        val prevItemPos = getPlanetIndexOfSelectedDest(curSelectedDestination)
        if (prevItemPos != null && prevItemPos != -1) {
            falconeViewModel.planetList?.get(prevItemPos)?.isSelected = false
        }
    }

    override fun selectedVehicle(pos: Int) {
        when (selectedItemId) {
            R.id.tietVehicleForDest1 -> {
                val prevSelectedVehicle = tietVehicleForDest1.text.toString()
                Log.d("log", "prevSelectedVehicle = $prevSelectedVehicle")
                unselectPrevSelectedVehicle(prevSelectedVehicle)
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
                unselectPrevSelectedVehicle(prevSelectedVehicle)
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
                unselectPrevSelectedVehicle(prevSelectedVehicle)
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
                unselectPrevSelectedVehicle(prevSelectedVehicle)
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
    }

    private fun getVehicleIndexOfSelectedVehicle(curSelectedVehicle: String): Int {
        var prevItemPos: Int = -1
        if (curSelectedVehicle != null && curSelectedVehicle.isNotEmpty()) {
            val dummyVehicleTO = VehiclesTO(curSelectedVehicle, 0, 0, 0, false)
            prevItemPos = falconeViewModel.vehicleList?.indexOf(dummyVehicleTO)!!
        }
        return prevItemPos
    }

    private fun unselectPrevSelectedVehicle(curSelectedVehicle: String) {
        val prevItemPos = getVehicleIndexOfSelectedVehicle(curSelectedVehicle)
        Log.d("log", "prevItemPos = $prevItemPos")
        if (prevItemPos != null && prevItemPos != -1) {
            falconeViewModel.vehicleList?.get(prevItemPos)?.vehiclesCount =
                    falconeViewModel.vehicleList?.get(prevItemPos)?.vehiclesCount?.plus(1)!!
        }
    }
}