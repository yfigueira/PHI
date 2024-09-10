import type {UserAuthentication} from "$lib/auth";
import {get, post} from "$lib/api";

const USER_AUTHENTICATION_PATH = "user-auth";

export type User = {
    id: string,
    firstName: string,
    lastName: string,
    email: string
}

export const loadUserData = (userAuthentication: UserAuthentication): Promise<User | void> => {
    const {email, token} = userAuthentication;

    return get(`${USER_AUTHENTICATION_PATH}/${email}`, token)
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }

            return response.json() as User;
        })
        .catch(err => {
            throw new Error(err)
        });
}

export const createUser = (userAuthentication: UserAuthentication): Promise<User | void> => {
    const {authId, firstName, lastName, email, token} = userAuthentication;

    return post(`${USER_AUTHENTICATION_PATH}`, {
        authId,
        firstName,
        lastName,
        email
    }, token)
        .then(response => response.json() as User)
        .catch(err => {
            throw new Error(err);
        });
}