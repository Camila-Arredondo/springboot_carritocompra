import { AuthConfig } from "@auth0/auth0-angular";

export const environment = {

    production: false,
    auth0:  {
      domain: 'camila-arredondo.us.auth0.com',
      clientId: 'rCjvrzKOJlKHtQWie9pBz0uaMxY9L89Y',
      authorizationParams: {
        redirect_uri: window.location.origin
        }
    }

};




