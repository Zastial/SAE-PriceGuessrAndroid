package fr.etudiant.priceguessr

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    var id : String,
    var date : String,
    var title : String,
    var price : Float,
    var imgSrc : String,
    var desc : String) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun toString(): String {
        return "{'id': '${id}', 'date':'${date}', 'title':'${title}','price':'${price}', 'imgSrc':'${imgSrc}', 'desc':'${desc}'}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(date)
        parcel.writeString(title)
        parcel.writeFloat(price)
        parcel.writeString(imgSrc)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
