package com.examly.springapp.service;

import com.examly.springapp.dto.ParcelRequest;
import com.examly.springapp.model.Parcel;

public interface ParcelService {

    Parcel bookParcel(ParcelRequest request);

    Parcel trackParcel(String trackingNumber);

    Parcel updateStatus(String trackingNumber, String status);
}
