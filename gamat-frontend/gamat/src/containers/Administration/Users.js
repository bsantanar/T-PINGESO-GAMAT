import React, { Component } from 'react';
import ListUser from './ListUser';
import { connect } from 'react-redux';
import * as actions from '../../store/actions/index';
import AddUser from './AddUser';
import AddIcon from '@material-ui/icons/Add';
import {Button} from 'reactstrap';
import Spinner from '../../components/UI/Spinner';

class Users extends Component {
    constructor(props) {
        super(props);
        this.state = {
            openModal:false,
            name:'',
            email:'',
            password:'',
            rol:0,
            roles:[
                'Manager'
                ,'Approver',
                'Buyer',
                'Driver']

        }
        this.toggleModal=this.toggleModal.bind(this)
        this.handlerOnAddUser=this.handlerOnAddUser.bind(this)
        this.handlerOnChangeForm=this.handlerOnChangeForm.bind(this)


    }

    handlerOnAddUser(){
        let newUser={
            name:this.state.name,
            email:this.state.email,
            password:this.state.password,
            role:this.state.roles[this.state.rol]
        }
        this.props.onAddUser(newUser)
        
        this.setState({
            name:'',
            email:'',
            password:'',
            rol:'',
            openModal:false
        })
    }

    handlerOnChangeForm(e){
        const value = e.target.value;
        const name = e.target.name;
        this.setState({
            [name]:value
        })
    }

    toggleModal(){
        this.setState({
            openModal:!this.state.openModal
        })
    }
    componentDidMount(){
        this.props.onFetchUsers();
    }
    render() {

        return (
            <div>
                 <Button color="primary" onClick={this.toggleModal}>Agregar <AddIcon/> </Button>
                <AddUser
                    name={this.state.name}
                    email={this.state.email}
                    password={this.state.password}
                    rol={this.state.rol}
                    roles={this.state.roles}
                    onChangeForm={this.handlerOnChangeForm}
                    onAddUser={this.handlerOnAddUser}
                    open={this.state.openModal}
                    toggle={this.toggleModal}
                />
                {this.props.loading ? <Spinner/>:
                <ListUser users={this.props.users} />
            }
            </div>
        );
    }
}



const mapStateToProps = state => {
    return {
        users: state.user.users,
        loading: state.user.loading

    };
};

const mapDispatchToProps = dispatch => {
    return {
        onFetchUsers: () => dispatch(actions.fetchUsers()),
        onAddUser: (newUser) => dispatch(actions.addUser(newUser)),


    };
};

export default connect(mapStateToProps, mapDispatchToProps)(Users);

