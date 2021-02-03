import React, {Component} from 'react';
import '@progress/kendo-theme-default/dist/all.css';
import './App.css';
import categories from './categories.json';
import products from './products.json';
import {process} from '@progress/kendo-data-query';
import {Grid, GridColumn} from '@progress/kendo-react-grid';
import {DropDownList} from '@progress/kendo-react-dropdowns';
import {Window} from '@progress/kendo-react-dialogs';

class App extends Component {
    constructor(props) {
        super(props);

        this.dataSource = new kendo.data.DataSource({
            transport: {
                read: {
                    url: "/read",
                    type: "GET",
                    dataType: "json",
                    contentType: "application/json"
                },
                update: {
                    url: "/update",
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json"
                },
                destroy: {
                    url: "/delete",
                    type: "DELETE",
                    dataType: "json",
                    contentType: "application/json"
                },
                create: {
                    url: "/create",
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json"
                },
                parameterMap: function (options, operation) {
                    if (operation !== "read" && options.models) {
                        console.log(JSON.stringify(options));
                        return JSON.stringify(options);
                    }
                }
            },
            batch: true,
            pageSize: 10,
            schema: {
                model: {
                    id: "id",
                    fields: {
                        title: {validation: {required: true}},
                        content: {validation: {required: true}},
                        this_date: {validation: {required: true}}
                    }
                }
            },
        });
    }

    render() {
        return (
            <Grid dataSource={this.dataSource}
                  pageable={true}
                  height={550}
                  toolbar={["create"]}
                  editable={"inline"}>

                <GridColumn field="title" title="제목"  />
                <GridColumn field="content" title="내용"  />
                <GridColumn field="this_date" title="날짜"  />
                <GridColumn command={["edit", "destroy"]} title="&nbsp;" width="200px" />
            </Grid>
        );
    }
}

export default App;