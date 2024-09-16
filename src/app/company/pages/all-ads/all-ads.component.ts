import { Component } from '@angular/core';
import { CompanyService } from '../../services/company.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-all-ads',
  templateUrl: './all-ads.component.html',
  styleUrl: './all-ads.component.scss'
})
export class AllAdsComponent {

  ads:any;

  constructor(private companyService:CompanyService,
    private notification: NzNotificationService
  ){}

  ngOnInit(){
    this.getAllAdsByUserId();
  }

    getAllAdsByUserId(){
      this.companyService.getAllAdsByUserId()
      .subscribe(res =>{
        this.ads=res;

      })
    }

    updateImg(img: string): string {
      return `data:image/jpeg;base64,${img}`;
    }   
    onImageError(event: Event) {
      console.error('Image failed to load', event);
    }

    deleteAd(adId:any){
      this.companyService.deleteAd(adId)
      .subscribe(res =>{
        this.notification
        .success(
          'SUCCESS',
          `Ad Deleted Successfully!`,
          {nzDuration: 5000}
        );
        this.getAllAdsByUserId();
      })
    }

}

