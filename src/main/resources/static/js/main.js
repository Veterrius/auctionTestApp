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
        itemAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.description = newVal.description;
            this.price = newVal.price;
            this.id = newVal.id;
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
                        let index = getIndex(this.items, data.id);
                        this.items.splice(index, 1, data);
                    }
                ));
            } else {
                itemApi.save({}, item).then(result => result.json().then(
                    data => {
                        this.items.push(data);
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
    props: ['item', 'editItem'],
    template: '<div>' +
        '<i>({{item.id}})</i> {{item.name}} {{item.price}}' +
        '<span>'+
          '<input type="button" value="Edit" @click="edit"/>'+
        '</span>'+
        '</div>',
    methods: {
        edit: function () {
            this.editItem(this.item)
        }
    }
});

Vue.component('items-list', {
    props: ['items', 'itemAttr'],
    data: function() {
        return {
            item: null
        }
    },
    template: "<div>" +
        "<item-form :items='items' :itemAttr='itemAttr'/>" +
        "<item-row v-for='item in items' :key='item.id' :item='item' :editItem='editItem'/>" +
        "</div>",
    created: function () {
        itemApi.get().then(result =>
            result.json().then(
                data => data.forEach(item => this.items.push(item))
            ))
    },
    methods: {
        editItem: function(item) {
            this.item = item;
        }
    }
});

let app = new Vue({
    el: '#app',
    template: '<items-list :items="items"/>',
    data: {
        items: []
    }
})
