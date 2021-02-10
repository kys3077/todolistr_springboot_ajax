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

export const getItems = (item) => {
    data = item;
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

export const deleteItem = async(item) => {
    let index = data.findIndex(record => record.id === item.id);
    data.splice(index, 1);
    await axios({
        method: "delete",
        url: "/delete",
        data: item
    })
        .then((Response) => {
            console.log(Response);
        }).catch((Error) => {
        console.log(Error);
    });
    return data;
};