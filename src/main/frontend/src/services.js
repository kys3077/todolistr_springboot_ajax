import { sampleProducts } from "./sample-products.jsx";

let data = [...sampleProducts];
import axios from 'axios'

const generateId = data =>
    data.reduce((acc, current) => Math.max(acc, current.ProductID), 0) + 1;

export const insertItem = item => {
    item.ProductID = generateId(data);
    item.inEdit = false;
    data.unshift(item);
    return data;
};

export const getItems = () => {
    return data;
};

export const updateItem = item => {
    let index = data.findIndex(record => record.ProductID === item.ProductID);
    console.log("index = " + index);
    data[index] = item;
    console.log("data index = " + item.ProductID);
    console.log("product Name = " + item.ProductName);
    console.log(item[item.ProductID]);
    return data;
};

export const deleteItem = item => {
    let index = data.findIndex(record => record.ProductID === item.ProductID);
    data.splice(index, 1);
    return data;
};