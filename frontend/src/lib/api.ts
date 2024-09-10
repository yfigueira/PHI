import {PUBLIC_API_BASE_URL} from "$env/static/public";

const enum HttpMethod {
    GET = 'GET',
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE',
    PATCH = 'PATCH'
}

interface RequestOptions {
    method: HttpMethod;
    path: string;
    data?: Record<string, any>;
    token?: string;
}

function send({method, path, data, token}: RequestOptions) {
    const headers = new Headers(data ? [['Content-Type', 'application/json']] : []);
    if (token) {
        headers.set('Authorization', `Bearer ${token}`);
    }

    const requestData: RequestInit = {
        method: method,
        headers: headers,
        body: data ? JSON.stringify(data) : null,
    }

    return fetch(`${PUBLIC_API_BASE_URL}/${path}`, requestData);
}

export const get = (path: string, token?: string) => {
    return send({method: HttpMethod.GET, path, token});
}

export const post = (path: string, data?: {}, token?: string) => {
    return send({method: HttpMethod.POST, path, data, token});
}

export const put = (path: string, data?: {}, token?: string) => {
    return send({method: HttpMethod.PUT, path, data, token});
}

export const del = (path: string, token?: string) => {
    return send({method: HttpMethod.DELETE, path, token})
}

export const patch = (path: string, data?: {}, token?: string) => {
    return send({method: HttpMethod.PATCH, path, data, token});
}