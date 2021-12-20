let authApi = Vue.resource('api/auth{/id}');

Vue.component('login-form', {
    data: function() {
        return {
            email:'',
            password:''
        }
    },
    template: "<div>" +
        "<div>Please login</div>" +
        "<div>" +
        "<input type='text' placeholder='Email' v-model='email'>" +
        "<input type='text' placeholder='Password' v-model='password'>" +
        "<input type='text' placeholder='Password' v-model='password'>" +
        "</div>"
});


let app = new Vue({
        el: '#loginApp',
        template: {}
    }
)

