import axios from 'axios'

let data = [];

export const insertItem = item => {
    item.inEdit = false;
    data.unshift(item);
    console.log(item);

    axios({
        method: "post",
        url: "/create",
        data: item
    })
        .then((Response) => {
            console.log(Response);
        }).catch((Error) => {
        console.log(Error);
    });
    return data;
};

export const getItems = () => {
    axios.get('/read').then((Response) => {
        console.log(Response.data);
        data = Response.data;
    }).catch((Error) => {
        console.log(Error);
    })
    return data;
};

export const updateItem = item => {
    let index = data.findIndex(record => record.id === item.id);
    data[index] = item;

    axios({
        method: "post",
        url: "/update",
        data: item
    })
        .then((Response) => {
            console.log(Response);
        }).catch((Error) => {
        console.log(Error);
    });

    return data;
};

export const deleteItem = item => {
    let index = data.findIndex(record => record.ProductID === item.ProductID);
    data.splice(index, 1);
    return data;
};