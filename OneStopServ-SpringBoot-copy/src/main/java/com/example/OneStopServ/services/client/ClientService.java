package com.example.OneStopServ.services.client;

import com.example.OneStopServ.dto.AdDTO;
import com.example.OneStopServ.dto.AdDetailsForClientDTO;
import com.example.OneStopServ.dto.ReservationDTO;
import com.example.OneStopServ.dto.ReviewDTO;

import java.util.List;

public interface ClientService {

    List<AdDTO> getAllAds();

    List<AdDTO> searchAdByName(String name);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsByAdId(Long adId);

    List<ReservationDTO> getAllBookingsByUserId(Long userId);

    boolean giveReview(ReviewDTO reviewDTO);
}
