function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
        return -1;
    }
}

let itemApi = Vue.resource('/rest/item{/id}');

Vue.component("item-form", {
    props: ['items', 'itemAttr'],
    data: function () {
        return {
            id: '',
            name: '',
            description: '',
            price: ''
        }
    },
    watch: {
        itemAttr: function (newVal) {
            this.id = newVal.id;
            this.name = newVal.name;
            this.description = newVal.description;
            this.price = newVal.price;
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="Name" v-model="name"><br>' +
        '<input type="text" placeholder="Description" v-model="description"><br>' +
        '<input type="number" step="0.01" required=required placeholder="Price" v-model="price"><br>' +
        '<input type="button" value="Create Item" @click="create">' +
        '</div>',
    methods: {
        create: function () {
            let item = {
                name: this.name,
                description: this.description,
                price: this.price
            };
            if (this.id) {
                itemApi.update({id: this.id}, item).then(result => result.json().then(
                    data => {
                        let index = getIndex(this.items, this.id);
                        this.items.splice(index, 1, data);
                        this.name = '';
                        this.description = '';
                        this.price = '';
                    }
                ))
            } else {
                itemApi.save({}, item).then(result => result.json().then(
                    data => {
                        this.items.push(data);
                        this.id = '';
                        this.name = '';
                        this.description = '';
                        this.price = '';
                    }
                ));
            }
        }
    }
});

Vue.component("item-row", {
    props: ['item', 'editMethod', 'items'],
    template: '<div>' +
        '<i>({{item.id}})</i> {{item.name}} {{item.price}}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" @click="edit"/>' +
        '<input type="button" value="x" @click="del"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.item)
        },
        del: function () {
            itemApi.remove({id: this.item.id}).then(result => {
                    if (result.ok) {
                        this.items.splice(this.items.indexOf(this.item), 1);
                    }
                }
            )
        }
    }
});

Vue.component('items-list', {
    props: ['items'],
    data: function () {
        return {
            itemAttr: null
        }
    },
    template: "<div style='position: relative; width: 300px'>" +
        "<item-form :items='items' :itemAttr='itemAttr'/>" +
        "<item-row v-for='item in items' :key='item.id' :items='items' :item='item' :editMethod='editMethod'/>" +
        "</div>",
    methods: {
        editMethod: function (item) {
            this.itemAttr = item;
        }
    }
});

let app = new Vue({
    el: '#app',
    template: '<items-list :items="items"/>',
    data: {
        items: []
    },
    created: function () {
        itemApi.get().then(result =>
            result.json().then(
                data => data.forEach(item => this.items.push(item))
            ))
    },
})
