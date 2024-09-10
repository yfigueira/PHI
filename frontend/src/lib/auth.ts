import Keycloak from "keycloak-js";
import {
    PUBLIC_AUTH_SERVER_URL,
    PUBLIC_BASE_URL,
    PUBLIC_KC_CLIENT_ID,
    PUBLIC_KC_REALM
} from "$env/static/public";

export type UserAuthentication = {
    authId: string
    email: string,
    firstName: string,
    lastName: string,
    token: string | undefined
}

export class AuthorizationService {
    private static instance: AuthorizationService;
    private _provider: Keycloak;

    private constructor() { }

    static getInstance(): AuthorizationService {
        if (!AuthorizationService.instance) AuthorizationService.instance = new AuthorizationService();
        return AuthorizationService.instance;
    }

    private get provider(): Keycloak {
        if (!this._provider) {
            this._provider = new Keycloak({
                url: PUBLIC_AUTH_SERVER_URL,
                realm: PUBLIC_KC_REALM,
                clientId: PUBLIC_KC_CLIENT_ID
            });
        }
        return this._provider;
    }

    async init(): Promise<boolean> {
        await this.provider.init({
            onLoad: "login-required",
            redirectUri: `${PUBLIC_BASE_URL}/home`
        });
    }

    async login(): Promise<void> {
        return this.provider.login({
            redirectUri: `${PUBLIC_BASE_URL}/home`
        });
    }

    async logout(): Promise<void> {
        return this.provider.logout();
    }

    async getUserInfo(): Promise<UserAuthentication> {
        const {id, email, firstName, lastName} = await this.provider.loadUserProfile();
        const token = this.provider.token
        return {
            authId: id,
            email,
            firstName,
            lastName,
            token
        }
    }
}