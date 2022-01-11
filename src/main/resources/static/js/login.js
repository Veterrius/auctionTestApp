let loginApi = Vue.resource('/api/auth/login');

Vue.component('login-form', {
    data: function() {
        return {
            email:'',
            password:''
        }
    },
    template: "<div>" +
        "<div>Please login</div>" +
        "<input type='text' placeholder='Email' v-model='email'>" +
        "<input type='text' placeholder='Password' v-model='password'>" +
        "<input type='button' value='Login' @click='login'>" +
        "</div>",
    methods: {
        login: function () {
            let request = {
                email: this.email,
                password: this.password
            }
            loginApi.save(request).then(result => result.json().then(
                data => {
                    this.email = '';
                    this.password = '';
                    window.location.replace("http://localhost:8080/auth/success");
                }
            ))
        }
    }
});


let app = new Vue({
        el: '#loginApp',
        template: "<login-form>"
    }
)

