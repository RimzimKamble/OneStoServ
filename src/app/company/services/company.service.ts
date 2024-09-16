import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../../basic/services/storage/user-storage.service';
import { Observable } from 'rxjs';

const Basic_URL="http://localhost:8080/";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http:HttpClient) { }

  postAd(adDTO:any):Observable<any>{
    const userId=UserStorageService.getUserId();
    return this.http.post(Basic_URL + `api/company/ad/${userId}`,adDTO, {
      headers: this.createAuthorizationHeader()
    }
  )
  }

  getAllAdsByUserId():Observable<any>{
    const userId=UserStorageService.getUserId();
    return this.http.get(Basic_URL + `api/company/ads/${userId}`,{
      headers: this.createAuthorizationHeader()
    }
  )
  }

  getAdById(adId:any):Observable<any>{
    return this.http.get(Basic_URL + `api/company/ad/${adId}`,{
      headers: this.createAuthorizationHeader()
    }
  )
  }

  updateAd(adId:any,adDTO:any):Observable<any>{

    return this.http.put(Basic_URL + `api/company/ad/${adId}`,adDTO, {
      headers: this.createAuthorizationHeader()
    })
  }

  deleteAd(adId:any):Observable<any>{

    return this.http.delete(Basic_URL + `api/company/ad/${adId}`, {
      headers: this.createAuthorizationHeader()
    })
  }

  getAllAdBookings():Observable<any>{

    const companyId= UserStorageService.getUserId();
    return this.http.get(Basic_URL + `api/company/bookings/${companyId}`,{
      headers: this.createAuthorizationHeader()
    }
  )
  }

  changeBookingStatus(bookingId:number, status:string):Observable<any>{

    return this.http.get(Basic_URL + `api/company/booking/${bookingId}/${status}`,{
      headers: this.createAuthorizationHeader()
    }
  )
  }

  createAuthorizationHeader():HttpHeaders{
    let authHeaders:HttpHeaders=new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer '+ UserStorageService.getToken()
    )
  }
}
