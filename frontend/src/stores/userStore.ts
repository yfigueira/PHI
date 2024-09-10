import type {Writable} from "svelte/store";
import {writable} from "svelte/store";

export type User = {
    id: string,
    firstName: string,
    lastName: string,
    email: string
}

export const user: Writable<User> = writable({});