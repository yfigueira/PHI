import type {LayoutLoad} from "./$types";
import {AuthorizationService} from "$lib/auth";
import type {UserAuthentication} from "$lib/auth";
import {createUser, loadUserData} from "$lib/user";
import type {User} from "$lib/user";

export const ssr = false;

export const load: LayoutLoad = async () => {

const authorizationService = AuthorizationService.getInstance();
await authorizationService.init();

const userAuthentication: UserAuthentication = await authorizationService.getUserInfo();

let userData: User | void;

try {
    userData = await loadUserData(userAuthentication);
} catch (Error) {
    userData = await createUser(userAuthentication);
}

    /*
    * For development:
    */
    // const userData = {
    //     id: "444292ea-469d-4787-a2bf-d5835949d494",
    //     firstName: "Dev",
    //     lastName: "User",
    //     email: "devuser@email.com"
    // }

    return {
        userData
    };
}