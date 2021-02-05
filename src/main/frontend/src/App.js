import * as React from "react";
import '@progress/kendo-theme-default/dist/all.css';
import {
    Grid,
    GridColumn as Column,
    GridToolbar,
} from "@progress/kendo-react-grid";
import axios from 'axios'

import {MyCommandCell} from "./myCommandCell.jsx";
import {insertItem, getItems, updateItem, deleteItem} from "./services.js";

class App extends React.Component {

    pageChange = (event) => {
        this.setState({
            skip: event.page.skip,
            take: event.page.take,
        });
    };
    editField = "inEdit";
    state = {
        skip: 0,
        take: 5,
        data: [],
    };

    getItem = async () => {
        axios
            .get('/read')
            .then((Response) => {
                this.setState({
                    data: Response.data,
                });
                console.log(Response.data);
            }).catch((Error) => {
            console.log(Error);
        })
    };

    componentDidMount() {
        getItems();
        this.getItem();
    }

    CommandCell = (props) => (
        <MyCommandCell
            {...props}
            edit={this.enterEdit}
            remove={this.remove}
            add={this.add}
            discard={this.discard}
            update={this.update}
            cancel={this.cancel}
            editField={this.editField}
        />
    );

    // modify the data in the store, db etc
    remove = (dataItem) => {
        const data = deleteItem(dataItem);
        this.setState({data});
    };

    add = (dataItem) => {
        dataItem.inEdit = true;

        const data = insertItem(dataItem);
        this.setState({
            data: data,
        });
    };

    update = (dataItem) => {
        dataItem.inEdit = false;
        const data = updateItem(dataItem);
        console.log(data);
        this.setState({data});
    };

    // Local state operations
    discard = (dataItem) => {
        const data = [...this.state.data];
        data.splice(0, 1);
        this.setState({data});
    };

    cancel = (dataItem) => {
        const originalItem = getItems().find(
            (p) => p.id === dataItem.id
        );
        const data = this.state.data.map((item) =>
            item.id === originalItem.id ? originalItem : item
        );

        this.setState({data});
    };

    enterEdit = (dataItem) => {
        this.setState({
            data: this.state.data.map((item) =>
                item.id === dataItem.id ? {...item, inEdit: true} : item
            ),
        });
    };

    itemChange = (event) => {
        const data = this.state.data.map((item) =>
            item.id === event.dataItem.id
                ? {...item, [event.field]: event.value}
                : item
        );

        this.setState({data});
    };

    addNew = () => {
        const newDataItem = {inEdit: true};

        this.setState({
            data: [newDataItem, ...this.state.data],
        });
    };

    render() {
        return (
            console.log("render"),
                console.log(this.state.data),
                <Grid
                    style={{height: "420px"}}
                    // data={this.state.data}
                    data={this.state.data.slice(
                        this.state.skip,
                        this.state.take + this.state.skip
                    )}
                    skip={this.state.skip}
                    take={this.state.take}
                    total={this.state.data.length}
                    pageable={true}
                    onPageChange={this.pageChange}
                    onItemChange={this.itemChange}
                    editField={this.editField}
                >
                    <GridToolbar>
                        <button
                            title="Add new"
                            className="k-button k-primary"
                            onClick={this.addNew}
                        >
                            Add new
                        </button>
                    </GridToolbar>
                    <Column field="id" title="Id" width="50px" editable={false}/>
                    <Column field="title" title="제목" width="200px"/>
                    <Column field="content" title="내용" width="200px"/>
                    {/*<Column*/}
                    {/*    field="this_date"*/}
                    {/*    title="날짜"*/}
                    {/*    editor="date"*/}
                    {/*    format="{0:d}"*/}
                    {/*    width="150px"*/}
                    {/*/>*/}
                    <Column cell={this.CommandCell} width="200px"/>
                </Grid>
        );
    }
}

export default App;
