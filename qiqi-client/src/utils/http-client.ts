import axios from 'axios';
import { AxiosRequestConfig } from 'axios';
import StandardError from 'standard-error';

const getPromise = (url: string, header?: AxiosRequestConfig): Promise<any> => {
  return axios
    .get(url, header)
    .then((response: any) => {
      return Promise.resolve(response.data);
    })
    .catch((error: any) => {
      if (error && error.response && error.response.data) {
        const errorCode = error.response.data.error;
        const msg = errorCode;
        return Promise.reject(new StandardError(msg, error.response.data));
      } else {
        return Promise.reject(new StandardError('SystemError', error));
      }
    });
};
const putPromise = (url: string, data?: any): Promise<any> => {
  return axios
    .put(url, data)
    .then((response: any) => {
      return Promise.resolve(response.data);
    })
    .catch((error: any) => {
      if (error && error.response && error.response.data) {
        const errorCode = error.response.data.error;
        const msg = errorCode;
        return Promise.reject(new StandardError(msg, error.response.data));
      } else {
        return Promise.reject(new StandardError('SystemError', error));
      }
    });
};
const postPromise = (url: string, data?: any, config?: AxiosRequestConfig): Promise<any> => {
  return axios
    .post(url, data, config)
    .then((response: any) => {
      return Promise.resolve(response.data);
    })
    .catch((error: any) => {
      if (error && error.response && error.response.data) {
        const errorCode = error.response.data.error;
        const msg = errorCode;
        return Promise.reject(new StandardError(msg, error.response.data));
      } else {
        return Promise.reject(new StandardError('SystemError', error));
      }
    });
};
const deletePromise = (url: string, config?: AxiosRequestConfig): Promise<any> => {
  return axios
    .delete(url, config)
    .then((response: any) => {
      return Promise.resolve(response.data);
    })
    .catch((error: any) => {
      if (error && error.response && error.response.data) {
        const errorCode = error.response.data.error;
        const msg = errorCode;
        return Promise.reject(new StandardError(msg, error.response.data));
      } else {
        return Promise.reject(new StandardError('SystemError', error));
      }
    });
};
export { getPromise, postPromise, putPromise, deletePromise };
