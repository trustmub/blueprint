package com.example.trmubaiwa.blueprint.Models

import android.os.Parcel
import android.os.Parcelable

class UserParcel(val id: String, val name: String, val email: String, val phone: String, val company: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(company)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserParcel> {
        override fun createFromParcel(parcel: Parcel): UserParcel {
            return UserParcel(parcel)
        }

        override fun newArray(size: Int): Array<UserParcel?> {
            return arrayOfNulls(size)
        }
    }
}