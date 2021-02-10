import '@progress/kendo-theme-default/dist/all.css';
import {
    Grid,
    GridColumn as Column,
    GridToolbar,
} from "@progress/kendo-react-grid";
import axios from 'axios'

import {MyCommandCell} from "./myCommandCell.jsx";
import {insertItem, getItems, updateItem, deleteItem} from "./services.js";
import Subject from "./components/Subject";
import {useEffect, useState} from "react";

function App() {
    const [skip, setSkip] = useState(0);
    const [take, setTake] = useState(5);
    const [data, setData] = useState([]);
    const [pageInfo, setPageInfo] = useState({});

    const pageChange = (event) => {
        setSkip(event.page.skip);
    };
    const editField = "inEdit";

    const getItem = () => {
        axios({
            method: 'get',
            url: '/list',
            params: {
                page: (skip / take),
                size: take
            }
        })
            .then((Response) => {
                setPageInfo(Response.data);
                setData(Response.data.content);
                getItems(Response.data.content);
            }).catch((Error) => {
            console.log(Error);
        });
    };

    useEffect(() => {
        getItem();
        console.log("useEffect", data);
    }, [skip]);

    const CommandCell = (props) => (
        <MyCommandCell
            {...props}
            edit={enterEdit}
            remove={remove}
            add={add}
            discard={discard}
            update={update}
            cancel={cancel}
            editField={editField}
        />
    );

    // modify the data in the store, db etc
    const remove = async (dataItem) => {
        const temp = deleteItem(dataItem);
        console.log("remove", temp);
        // setData(temp);
        getItem();
        console.log("delete");
    };


    const add = (dataItem) => {
        dataItem.inEdit = true;

        const temp = insertItem(dataItem);
        setData(temp);
    };


    const update = (dataItem) => {
        dataItem.inEdit = false;
        const temp = updateItem(dataItem);
        setData(temp);
    };
    // Local state operations
    const discard = (dataItem) => {
        const temp = [...data];
        temp.splice(0, 1);
        setData(temp);
    };

    const cancel = (dataItem) => {
        console.log("cancel", dataItem);
        const originalItem = data.find(
            p => p.id === dataItem.id
        );
        const temp = data.map((item) =>
            item.id === originalItem.id ? originalItem : item
        );
        console.log("calcel2", temp);

        dataItem.inEdit = false;
        setData(temp);
    };

    const enterEdit = (dataItem) => {
        const temp = data.map((item) =>
            item.id === dataItem.id ? {...item, inEdit: true} : item
        );
        setData(temp);
    };

    const itemChange = (event) => {
        const temp = data.map((item) =>
            item.id === event.dataItem.id
                ? {...item, [event.field]: event.value}
                : item
        );

        setData(temp);
    };


    const addNew = () => {
        const newDataItem = {inEdit: true, Discontinued: false};
        const temp = [newDataItem, ...data];
        setData(temp);
    };

    return (
        console.log("return", data),
            <div>
                <Subject/>
                <Grid
                    style={{height: "420px"}}
                    data={data}
                    skip={skip}
                    take={take}
                    total={pageInfo.totalElements}
                    pageable={true}
                    onPageChange={pageChange}
                    onItemChange={itemChange}
                    editField={editField}
                >
                    <GridToolbar>
                        <button
                            title="Add new"
                            className="k-button k-primary"
                            onClick={addNew}
                        >
                            Add new
                        </button>
                    </GridToolbar>
                    <Column field="id" title="Id" width="50px" editable={false}/>
                    <Column field="title" title="제목" width="200px"/>
                    <Column field="content" title="내용" width="500px"/>
                    <Column field="this_date" title="날짜" width="200px"/>
                    {/*<Column*/}
                    {/*    field="this_date"*/}
                    {/*    title="날짜"*/}
                    {/*    editor="date"*/}
                    {/*    format="{0:d}"*/}
                    {/*    width="150px"*/}
                    {/*/>*/}
                    <Column cell={CommandCell} width="200px"/>
                </Grid>
            </div>
    );

}

export default App;
