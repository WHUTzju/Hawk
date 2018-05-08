pragma solidity ^0.4.15;

contract Hermes{
    function setSth(bytes32 param) returns(uint,bytes32){
        return (1,param);
    }
}