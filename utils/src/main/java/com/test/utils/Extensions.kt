package com.test.utils

import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.floriaapp.core.domain.models.order_response.OrderItem
import com.floriaapp.core.domain.models.user.User
import com.floriaapp.core.ui.OrderViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.test.utils.App.Companion.context
import com.test.utils.Bases.SafeClickListener
import com.test.utils.Common.di.getSharedPrefrences
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.util.*


fun Context.sendMessageToWatsNumber(number: String) {
    val url = "$WATSAPP_URL$number"
    try {
        val pm: PackageManager = this.packageManager
        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        if (i.resolveActivity(packageManager) != null) (this as Activity).startActivity(i);
    } catch (e: PackageManager.NameNotFoundException) {
        Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show()
    }
}
fun Context.showLargeImage(urlOfImage: String) {
    val builder = Dialog(this)
    builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
    builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    builder.setCanceledOnTouchOutside(true)
    val imageView = ImageView(this)
    Picasso.get().load(urlOfImage).into(imageView)
    builder.addContentView(imageView, RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    builder.show()
}

fun Context?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("HardwareIds")
fun Context?.getDeviceID(): String? {
    return Settings.Secure.getString(App.getAppContext().contentResolver,
            Settings.Secure.ANDROID_ID)
}
fun String.toBold(string: String): Spanned? {
    return Html.fromHtml("<b>" + string + "</b>")

}

fun Context?.makeCall(phone: String?) {
    if(phone != null) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        this?.startActivity(intent)
    }
}

fun Context.getCompleteAddress(data: OrderItem?): String? {
    val address = data?.address?.get(0)
    val dash = "-"
//    val completeAddres = "${address?.buildingNumber ?: " "} " + address?.name + "," + address?.district?.name + ", " + address?.streetName + " \n ${resources.getString(R.string.apartment_number_title)} : " + " ${address?.apartmentNumber ?: resources.getString(R.string.apartment_number_title)}"
    // else completeAddres = address?.name + "-" + "${address?.district?.name}"
    val completeAddres: String = "${address?.buildingNumber ?: " "} " + address?.name

    return completeAddres
}

fun Context.setPaymentType(data: Int, payment_pots: TextInputEditText, paymentMethod: String) {
    if (data == 1 && paymentMethod == "PAYATFAWRY") {
        payment_pots.setText(R.string.fawry)
        payment_pots.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fawry, 0);

    }
    if (data == 2) {
        payment_pots.setText(R.string.cash)
        payment_pots.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cash, 0);

    }
    if (data == 1 && paymentMethod == "CARD") {
        payment_pots.setText(R.string.visa)
        payment_pots.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visa_image, 0);

    }
}

fun Context?.setLocale(langugeNeeded: String) {
    val locale = Locale(langugeNeeded)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    this?.resources?.updateConfiguration(
            config,
            this.resources.displayMetrics
    )
}

fun Context.showAlertDialog(title: String, message: String, launchFunction: () -> Unit) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
        launchFunction()
    }
    builder.setNeutralButton(resources.getString(R.string.no)) { dialog, which ->
        dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

fun Context.triggerRebirth() {
    val packageManager = context.packageManager
    val intent = packageManager.getLaunchIntentForPackage(context.packageName);
    val componentName = intent?.component
    val mainIntent = Intent.makeRestartActivityTask(componentName);
    context.startActivity(mainIntent);
    Runtime.getRuntime().exit(0)

}


fun Context.showMethod(ctx: Context, orderNumber: Int, viewModel: OrderViewModel, orderStatusRecieved: Int) {
    val mDialogView = LayoutInflater.from(ctx).inflate(R.layout.dialog_layout, null)
    val mBuilder = AlertDialog.Builder(ctx).setView(mDialogView)
    val mAlertDialog = mBuilder.show()
    Log.i("status", orderStatusRecieved.toString())
    when (orderStatusRecieved) {

        STATUS_NEW -> {
            closeShippedButton(mDialogView, ctx)
            closeDeliverdButton(mDialogView, ctx)
        }
        STATUS_INPROGRESS -> {
            closeInProgress(mDialogView, ctx)
            closeDeliverdButton(mDialogView, ctx)
            closeCancell(mDialogView, ctx)
        }
        STATUS_SHIPPED -> {
            closeInProgress(mDialogView, ctx)
            closeShippedButton(mDialogView, ctx)
            closeCancell(mDialogView, ctx)
        }
        STATUS_DELIVERD -> {
            closeInProgress(mDialogView, ctx)
            closeDeliverdButton(mDialogView, ctx)
            closeShippedButton(mDialogView, ctx)
            closeCancell(mDialogView, ctx)
        }
        STATUS_CANCELLED -> {
            closeInProgress(mDialogView, ctx)
            closeDeliverdButton(mDialogView, ctx)
            closeShippedButton(mDialogView, ctx)
            closeCancell(mDialogView, ctx)
        }

        else -> Log.i(javaClass.simpleName, "eshta 3lek")
    }
    mDialogView.findViewById<Button>(R.id.InProgress).setOnClickListener {
        viewModel.changeOrderStatus(orderNumber, STATUS_INPROGRESS)
        mAlertDialog.dismiss()

    }

    mDialogView.findViewById<Button>(R.id.Shipped).setOnClickListener {
        mAlertDialog.dismiss()
        viewModel.changeOrderStatus(orderNumber, STATUS_SHIPPED)

    }


    mDialogView.findViewById<Button>(R.id.Delivered).setOnClickListener {
        mAlertDialog.dismiss()
        viewModel.changeOrderStatus(orderNumber, STATUS_DELIVERD)

    }

    mDialogView.findViewById<Button>(R.id.Cancelld).setOnClickListener {
        mAlertDialog.dismiss()
        val bottomDialog = BottomSheetDialog(ctx)
        val view = (ctx as Activity).layoutInflater.inflate(R.layout.cancel_dialog, null)
        view.findViewById<Button>(R.id.btn_no).setOnClickListener { bottomDialog.dismiss() }
        view.findViewById<Button>(R.id.btn_yes).setOnClickListener {
            viewModel.changeOrderStatus(orderNumber, STATUS_CANCELLED)
            // ctx.finish()
            bottomDialog.dismiss()
        }
        bottomDialog.setContentView(view)
        bottomDialog.show()


    }

}

private fun closeCancell(mDialogView: View, ctx: Context) {
    mDialogView.findViewById<Button>(R.id.Cancelld).isEnabled = false
    mDialogView.findViewById<Button>(R.id.Cancelld).setTextColor(ContextCompat.getColor(ctx, R.color.colorgray))
}

private fun closeShippedButton(mDialogView: View, ctx: Context) {
    mDialogView.findViewById<Button>(R.id.Shipped).isEnabled = false
    mDialogView.findViewById<Button>(R.id.Shipped).setTextColor(ContextCompat.getColor(ctx, R.color.colorgray))
}

private fun closeInProgress(mDialogView: View, ctx: Context) {
    mDialogView.findViewById<Button>(R.id.InProgress).isEnabled = false
    mDialogView.findViewById<Button>(R.id.InProgress).setTextColor(ContextCompat.getColor(ctx, R.color.colorgray))
}

private fun closeDeliverdButton(mDialogView: View, ctx: Context) {
    mDialogView.findViewById<Button>(R.id.Delivered).isEnabled = false
    mDialogView.findViewById<Button>(R.id.Delivered).setTextColor(ContextCompat.getColor(ctx, R.color.colorgray))
}


fun ImageView.loadImage(src: String? = null, width: Int? = null, height: Int? = null, uri: Uri? = null) {
    if (width != null) {
        src?.let { srcImg ->
            Picasso.get().load(srcImg).placeholder(circularProgressDrawable(this.context)).resize(width, height!!).into(this)
        }
    } else {
        if (uri != null) Picasso.get().load(uri).placeholder(circularProgressDrawable(this.context)).fit().into(this)
        else
            src?.let { srcImg ->
                Picasso.get().load(srcImg).placeholder(circularProgressDrawable(this.context)).error(R.drawable.ic_floria).fit().into(this)
            }
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun circularProgressDrawable(mContext: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(mContext)
    circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(mContext, android.R.color.holo_red_dark))
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun <T> Context.saveList(key: String?, list: List<T>?) {
    val gson = Gson()
    val json: String = gson.toJson(list)
    getSharedPrefrences(androidApplication = this).edit().putString(key, json).apply()
}


fun SharedPreferences.Editor?.saveUserInfo(ctx: Context, user: User) {
    val gson = GsonBuilder().create()
    val toPass = gson.toJson(user)
    this?.putString(USER_PROFILE_DATA, toPass)?.apply()
}

fun SharedPreferences?.updateAvailabilty(isOnline: Boolean) {
    val gson = GsonBuilder().create()
    val toPass = this?.getString(USER_PROFILE_DATA, " ")
    val user = gson.fromJson<User>(toPass, User::class.java)
    user.isOnline = isOnline
    val toPass2 = gson.toJson(user)
    this?.edit()?.putString(USER_PROFILE_DATA, toPass2)?.apply()
}


fun SharedPreferences.getUserInfo(ctx: Context): User {
    val gson = GsonBuilder().create()
    val toPass = this.getString(USER_PROFILE_DATA, " ")
    val user = gson.fromJson<User>(toPass, User::class.java)
    return user

}

fun ShapeableImageView.toCirculr(): ShapeAppearanceModel {
    return this.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, 60f)
            .build().also { this.shapeAppearanceModel = it };
}


fun Context.getList(key: String): String? {
    return getSharedPrefrences(this).getString(key, null)
}


fun Context.chooseImagePicker(requestCode: Int) {
    val photoPickerIntent = Intent(Intent.ACTION_PICK)
    photoPickerIntent.type = "image/*"
    (this as Activity).startActivityForResult(photoPickerIntent, requestCode)
}

fun Context.bitmapToFile(bitmap: Bitmap): File {
    // Get the context wrapper
    val wrapper = ContextWrapper(this)
    val getImage: File? = filesDir
    val file = File(getImage?.path, "${UUID.randomUUID()}.jpg")
    try {
        // Compress the bitmap and save in jpg format
        val stream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream)
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return file
}

fun Context.toMultipart(uri: Uri, type: String): MultipartBody.Part {
    val imageStream: InputStream? = uri?.let { this.contentResolver.openInputStream(it) }
    val selectedImage = BitmapFactory.decodeStream(imageStream)
    val file = this.bitmapToFile(selectedImage)
    val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
    val multipartBody = MultipartBody.Part.createFormData(type, file.name, requestFile)
    return multipartBody

//    val input = this.contentResolver.openInputStream(uri)
//    val image = BitmapFactory.decodeStream(input , null, null)
//
//    // Encode image to base64 string
//    val baos = ByteArrayOutputStream()
//    image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//    val imageBytes = baos.toByteArray()
//    val imageString = Base64.getEncoder().encodeToString(imageBytes)
//    return imageString
}

fun Context.getBitmap(uri: Uri): Bitmap? {
    val imageStream: InputStream? = uri?.let { this.contentResolver.openInputStream(it) }
    val selectedImage = BitmapFactory.decodeStream(imageStream)
    return selectedImage
}
fun Context.getScreenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    val height: Int = displayMetrics.heightPixels
    val width: Int = displayMetrics.widthPixels
    return width
}

fun Bitmap.BitmapToString(): String? {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

/**
 * To Json
 */
inline fun <reified T : Any> T?.json() = this?.let { Gson().toJson(this, T::class.java) }

/**
 * from  Json
 */
inline fun <reified T : Any> String?.fromJson(): T? = this?.let {
    val type = object : TypeToken<T>() {}.type
    Gson().fromJson(this, type)
}


fun Drawable.overrideColor_Ext(context: Context, colorInt: Int) {
    val muted = this.mutate()
    when (muted) {
        is GradientDrawable -> muted.setColor(ContextCompat.getColor(context, colorInt))
        is ShapeDrawable -> muted.paint.color = ContextCompat.getColor(context, colorInt)
        is ColorDrawable -> muted.color = ContextCompat.getColor(context, colorInt)
        else -> Log.d("Tag", "Not a valid background type")
    }
}

fun View.setMarginReight(rightMargin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(params.leftMargin, params.topMargin, rightMargin, params.bottomMargin)
    layoutParams = params
}

/**
 * Manages the various graphs needed for a [BottomNavigationView].
 *
 * This sample is a workaround until the Navigation Component supports multiple back stacks.
 */
fun BottomNavigationView.setupWithNavController(
        navGraphIds: List<Int>,
        fragmentManager: FragmentManager,
        containerId: Int,
        intent: Intent
): LiveData<NavController> {

    // Map of tags
    val graphIdToTagMap = SparseArray<String>()
    // Result. Mutable live data with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()

    var firstFragmentGraphId = 0

    // First create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
                fragmentManager,
                fragmentTag,
                navGraphId,
                containerId
        )

        // Obtain its id
        val graphId = navHostFragment.navController.graph.id

        if (index == 0) {
            firstFragmentGraphId = graphId
        }

        // Save to the map
        graphIdToTagMap[graphId] = fragmentTag

        // Attach or detach nav host fragment depending on whether it's the selected item.
        if (this.selectedItemId == graphId) {
            // Update livedata with the selected graph
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    // Now connect selecting an item with swapping Fragments
    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    // When a navigation item is selected
    setOnNavigationItemSelectedListener { item ->
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(firstFragmentTag,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                        as NavHostFragment

                // Exclude the first fragment tag because it's always in the back stack.
                if (firstFragmentTag != newlySelectedItemTag) {
                    // Commit a transaction that cleans the back stack and adds the first fragment
                    // to it, creating the fixed started destination.
                    fragmentManager.beginTransaction()
                            .attach(selectedFragment)
                            .setPrimaryNavigationFragment(selectedFragment)
                            .apply {
                                // Detach all other Fragments
                                graphIdToTagMap.forEach { _, fragmentTagIter ->
                                    if (fragmentTagIter != newlySelectedItemTag) {
                                        detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                    }
                                }
                            }
                            .addToBackStack(firstFragmentTag)
                            .setReorderingAllowed(true)
                            .commit()
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    // Optional: on item reselected, pop back stack to the destination of the graph
    setupItemReselected(graphIdToTagMap, fragmentManager)

    // Handle deep link
    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    // Finally, ensure that we update our BottomNavigationView when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun BottomNavigationView.setupDeepLinks(
        navGraphIds: List<Int>,
        fragmentManager: FragmentManager,
        containerId: Int,
        intent: Intent
) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
                fragmentManager,
                fragmentTag,
                navGraphId,
                containerId
        )
        // Handle Intent
        if (navHostFragment.navController.handleDeepLink(intent)
                && selectedItemId != navHostFragment.navController.graph.id) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(
        graphIdToTagMap: SparseArray<String>,
        fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
                navController.graph.startDestination, false
        )
    }
}

private fun detachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment
) {
    fragmentManager.beginTransaction()
            .detach(navHostFragment)
            .commitNow()
}

private fun attachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment,
        isPrimaryNavFragment: Boolean
) {
    fragmentManager.beginTransaction()
            .attach(navHostFragment)
            .apply {
                if (isPrimaryNavFragment) {
                    setPrimaryNavigationFragment(navHostFragment)
                }
            }
            .commitNow()

}

private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int,
        containerId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
            .add(containerId, navHostFragment, fragmentTag)
            .commitNow()
    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

// TODO: Step 1.1 extension function to send messages (GIVEN)
/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, messageTitle: String, applicationContext: Context) {
    val contentIntent = Intent(applicationContext, Class.forName(SPLASH_CLASS_NAME))
    contentIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    contentIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    val pendingIntent = PendingIntent.getActivity(
            applicationContext, NOTIFICATION_ID, contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
            applicationContext,
            "1"
    ).setContentTitle((messageTitle))
            .setContentText(messageBody)
            .setContentIntent(pendingIntent)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo_icon))
            .setSmallIcon(R.drawable.ic_floria)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelAllNotifications() {
    cancelAll()
}

fun NotificationManager.cancelSpecificNotifcation(id: Int) {
    cancel(id)
}


private fun getFragmentTag(index: Int) = "bottomNavigation#$index"