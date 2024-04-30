import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {HttpClient} from "@angular/common/http";
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";

const SETTING_ICON = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"20\" height=\"20\" viewBox=\"0 0 20 20\" fill=\"none\">\n" +
  "  <g clip-path=\"url(#clip0_307_2784)\">\n" +
  "    <path d=\"M12.5002 20H7.50019V17.0725C6.63124 16.7651 5.8264 16.3001 5.12602 15.7008L2.58936 17.1667L0.0893555 12.8333L2.62519 11.3708C2.45861 10.4645 2.45861 9.53546 2.62519 8.62917L0.0893555 7.16667L2.58936 2.83333L5.12602 4.29917C5.8264 3.69994 6.63124 3.23495 7.50019 2.9275V0H12.5002V2.9275C13.3691 3.23495 14.174 3.69994 14.8744 4.29917L17.411 2.83333L19.911 7.16667L17.3752 8.62917C17.5418 9.53546 17.5418 10.4645 17.3752 11.3708L19.911 12.8333L17.411 17.1667L14.8744 15.7017C14.1739 16.3006 13.3691 16.7653 12.5002 17.0725V20ZM9.16686 18.3333H10.8335V15.8108L11.4594 15.6492C12.486 15.3832 13.4205 14.8418 14.1619 14.0833L14.6144 13.6225L16.8002 14.885L17.6335 13.4417L15.4502 12.1808L15.6219 11.5592C15.9041 10.5366 15.9041 9.45672 15.6219 8.43417L15.4502 7.8125L17.6335 6.55167L16.8002 5.10833L14.6144 6.37417L14.1619 5.91667C13.4201 5.15945 12.4857 4.61918 11.4594 4.35417L10.8335 4.18917V1.66667H9.16686V4.18917L8.54102 4.35083C7.51435 4.61679 6.57986 5.15824 5.83852 5.91667L5.38602 6.3775L3.20019 5.11167L2.36686 6.555L4.55019 7.81583L4.37852 8.4375C4.09632 9.46005 4.09632 10.5399 4.37852 11.5625L4.55019 12.1842L2.36686 13.445L3.20019 14.8883L5.38602 13.6258L5.83852 14.0867C6.58026 14.8439 7.51471 15.3841 8.54102 15.6492L9.16686 15.8108V18.3333ZM10.0002 13.3333C9.34092 13.3333 8.69645 13.1378 8.14829 12.7716C7.60012 12.4053 7.17288 11.8847 6.92059 11.2756C6.6683 10.6665 6.60229 9.9963 6.7309 9.3497C6.85952 8.7031 7.17699 8.10915 7.64317 7.64298C8.10934 7.1768 8.70328 6.85933 9.34989 6.73072C9.99649 6.6021 10.6667 6.66811 11.2758 6.9204C11.8849 7.17269 12.4055 7.59994 12.7718 8.1481C13.138 8.69626 13.3335 9.34073 13.3335 10C13.3335 10.8841 12.9823 11.7319 12.3572 12.357C11.7321 12.9821 10.8842 13.3333 10.0002 13.3333ZM10.0002 8.33333C9.67055 8.33333 9.34832 8.43108 9.07424 8.61422C8.80016 8.79735 8.58654 9.05765 8.46039 9.36219C8.33424 9.66674 8.30124 10.0018 8.36555 10.3252C8.42986 10.6485 8.58859 10.9454 8.82168 11.1785C9.05477 11.4116 9.35174 11.5703 9.67504 11.6346C9.99834 11.699 10.3335 11.6659 10.638 11.5398C10.9425 11.4137 11.2028 11.2 11.386 10.926C11.5691 10.6519 11.6669 10.3296 11.6669 10C11.6669 9.55797 11.4913 9.13405 11.1787 8.82149C10.8661 8.50893 10.4422 8.33333 10.0002 8.33333Z\" fill=\"black\"/>\n" +
  "  </g>\n" +
  "  <defs>\n" +
  "    <clipPath id=\"clip0_307_2784\">\n" +
  "      <rect width=\"20\" height=\"20\" fill=\"white\"/>\n" +
  "    </clipPath>\n" +
  "  </defs>\n" +
  "</svg>\n";
const LOGOUT_ICON = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"20\" height=\"20\" viewBox=\"0 0 20 20\" fill=\"none\">\n" +
  "  <g clip-path=\"url(#clip0_307_2787)\">\n" +
  "    <path d=\"M18.9692 8.52686L14.8442 4.40186L13.6667 5.58352L17.25 9.16686H5.51416V10.8335H17.25L13.6667 14.4169L14.845 15.5952L18.97 11.4702C19.1635 11.2767 19.317 11.047 19.4217 10.7942C19.5265 10.5414 19.5804 10.2705 19.5804 9.99686C19.5804 9.72322 19.5265 9.45227 19.4217 9.19947C19.317 8.94667 19.1635 8.71698 18.97 8.52352L18.9692 8.52686Z\" fill=\"black\"/>\n" +
  "    <path d=\"M8.73 17.5C8.73 17.721 8.6422 17.933 8.48592 18.0893C8.32964 18.2455 8.11768 18.3333 7.89667 18.3333H2.5C2.27899 18.3333 2.06702 18.2455 1.91074 18.0893C1.75446 17.933 1.66667 17.721 1.66667 17.5V2.5C1.66667 2.27899 1.75446 2.06702 1.91074 1.91074C2.06702 1.75446 2.27899 1.66667 2.5 1.66667H7.89667C8.11768 1.66667 8.32964 1.75446 8.48592 1.91074C8.6422 2.06702 8.73 2.27899 8.73 2.5V6.94417H10.3967V2.5C10.3967 1.83696 10.1333 1.20107 9.66444 0.732233C9.19559 0.263392 8.55971 0 7.89667 0L2.5 0C1.83696 0 1.20107 0.263392 0.732233 0.732233C0.263392 1.20107 0 1.83696 0 2.5L0 17.5C0 18.163 0.263392 18.7989 0.732233 19.2678C1.20107 19.7366 1.83696 20 2.5 20H7.89667C8.55971 20 9.19559 19.7366 9.66444 19.2678C10.1333 18.7989 10.3967 18.163 10.3967 17.5V13.0558H8.73V17.5Z\" fill=\"black\"/>\n" +
  "  </g>\n" +
  "  <defs>\n" +
  "    <clipPath id=\"clip0_307_2787\">\n" +
  "      <rect width=\"20\" height=\"20\" fill=\"white\"/>\n" +
  "    </clipPath>\n" +
  "  </defs>\n" +
  "</svg>";
@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.scss'
})
export class ProfileMenuComponent {

  constructor(
    public userService: UserService,
    private http: HttpClient,
    iconRegistry: MatIconRegistry,
    sanitizer: DomSanitizer
  ) {
    iconRegistry.addSvgIconLiteral('setting', sanitizer.bypassSecurityTrustHtml(SETTING_ICON));
    iconRegistry.addSvgIconLiteral("logout", sanitizer.bypassSecurityTrustHtml(LOGOUT_ICON));
  }

  afterAuth(){
    this.http.get("http://localhost:8080/user", {withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }

}
