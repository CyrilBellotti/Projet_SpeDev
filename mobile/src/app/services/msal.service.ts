import { Injectable } from '@angular/core';

import * as Msal from 'msal';

declare var bootbox: "";
@Injectable()
export class MsalService {

    B2CTodoAccessTokenKey = "b2c.access.token";

    tenantConfig = {
        tenant: "atlantis71.onmicrosoft.com",
        clientID: '708141cc-522e-45f8-93c2-4f61608705ac',
        signInPolicy: "B2C_1_signin",
        signUpPolicy: "B2C_1_signup",
        b2cScopes:["https://altantis71.onmicrosoft.com/access-api/user_impersonation"]
    };

    // tenantConfig = {
    //     tenant: "atlantis73.onmicrosoft.com",
    //     // Replace this with your client id 
    //     clientID: '9954b49b-c038-4427-b7b1-0c2c27e137be',
    //     signInPolicy: "B2C_1_signin",
    //     signUpPolicy: "B2C_1_signup",
    //     //redirectUri:"http://localhost:8100/tabs/tab1",
    //     b2cScopes:["https://atlantis73.onmicrosoft.com/access-api/user_impersonation"]
    // };

    // Configure the authority for Azure AD B2C
    authority = "https://login.microsoftonline.com/tfp/" + this.tenantConfig.tenant + "/" + this.tenantConfig.signInPolicy;
    // authority = null;

    /*
     * B2C SignIn SignUp Policy Configuration
     */
    clientApplication = new Msal.UserAgentApplication(
        this.tenantConfig.clientID, this.authority,
        function (errorDesc: any, token: any, error: any, tokenType: any) {
      }
    );

    public login():void{
        this.clientApplication.authority = "https://login.microsoftonline.com/tfp/" + this.tenantConfig.tenant + "/" + this.tenantConfig.signInPolicy;
        this.authenticate();
    }

    public signup():void{
      this.clientApplication.authority = "https://login.microsoftonline.com/tfp/" + this.tenantConfig.tenant + "/" + this.tenantConfig.signUpPolicy;
      this.authenticate();
    }

    public authenticate(): void {
        var _this = this;
        this.clientApplication.loginPopup(this.tenantConfig.b2cScopes).then(function (idToken: any) {
            _this.clientApplication.acquireTokenSilent(_this.tenantConfig.b2cScopes).then(
                function (accessToken: any) {
                    _this.saveAccessTokenToCache(accessToken);
                }, function (error: any) {
                    _this.clientApplication.acquireTokenPopup(_this.tenantConfig.b2cScopes).then(
                        function (accessToken: any) {
                            _this.saveAccessTokenToCache(accessToken);
                        }, function (error: any) {
                            console.log("error: ", error);
                        });
                })

        }, function (error: any) {
            console.log("error: ", error);
        });
    }

    saveAccessTokenToCache(accessToken: string): void {
        sessionStorage.setItem(this.B2CTodoAccessTokenKey, accessToken);
    };

    logout(): void {
        this.clientApplication.logout();
    };

    isLoggedIn(): boolean {
        return this.clientApplication.getUser() != null;
    };

    getUserEmail(): string{
       return this.getUser().idToken['emails'][0];
    }

    getUser(){
      return this.clientApplication.getUser()
    }
}