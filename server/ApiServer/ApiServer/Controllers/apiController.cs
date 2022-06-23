using System;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;
using System.Text.Json.Serialization;
using Microsoft.AspNetCore.Authentication;
using ApiServer.Hubs;
using Microsoft.AspNetCore.SignalR;
using System.Diagnostics.CodeAnalysis;
using ApiServer.Hubs.Clients;
using ApiServer.Models;
using ApiServer.Services;

namespace ApiServer.Controllers;

[ApiController]
[Route("[controller]")]
public class apiController : ControllerBase
{
    private string serverPath = "https://localhost:7127";
    //private readonly UsersDBService usersDbService = new UsersDBService();
    private readonly Users1DBService users1DbService = new Users1DBService();
  //  private readonly AndroidMessaging androidService = new AndroidMessaging();
    private readonly IHubContext<DataBaseHub,IDataBaseClient> _messageHub;
    public apiController(IHubContext<DataBaseHub, IDataBaseClient> messageHub)
    {
        _messageHub = messageHub;
    }
    [HttpPost("android")]
    public IActionResult AddAndroidClient([FromBody] AndroidUsers? bodyRequest)
    {
        if (bodyRequest == null)
        {
            return BadRequest();
        }
        if (!users1DbService.AddNewAndroidClient(bodyRequest.id,bodyRequest.firebaseToken)){
            return BadRequest();
        }
        return Ok();
    }
    [HttpGet("contacts")]
    public IActionResult GetContacts(string? loggedUserId)
    {
        if (loggedUserId == null)
        {
            return BadRequest();
        }
        var contacts = users1DbService.GetAllContacts(loggedUserId);
        if (contacts == null)
        {
            return NotFound();
        }
        return Ok(JsonSerializer.Serialize(contacts));
    }

    [HttpGet("nickname")]
    public IActionResult GetNickname(string? loggedUserId)
    {
        if (loggedUserId == null)
        {
            return BadRequest();
        }
        var nickName = users1DbService.GetNickName(loggedUserId);
        return Ok(JsonSerializer.Serialize(nickName));
    }
    [HttpPost("register")]
    public IActionResult RegisterNewUser([FromBody] RegisterInput? bodyRequest)
    {
        if (bodyRequest == null)
        {
            return BadRequest();
        }
        if (!users1DbService.AddNewUser(bodyRequest.id,bodyRequest.nickName,bodyRequest.password)){
            return BadRequest();
        }
        return Ok();
    }
    [HttpPost("login")]
    public IActionResult Login([FromBody] LoginInput? bodyRequest)
    {
        if (bodyRequest == null)
        {
            return BadRequest();
        }
        if (!users1DbService.Login(bodyRequest.id,bodyRequest.password)){
            return BadRequest();
        }
        return Ok();
    }
    [HttpGet("register/{id}")]
    public IActionResult RegisterNewUser(string id)
    {
        bool result = users1DbService.IsUserExists(id);
        if (result)
        {
            return BadRequest();
        }
        return Ok();
    }
    [HttpGet("nickName/{id}")]
    public IActionResult getNickName(string id)
    {
        if (id == null)
        {
            return BadRequest();
        }
        var nickName = users1DbService.GetNickName(id);
        return Ok(JsonSerializer.Serialize(nickName));
    }
    [HttpPost("contacts")]
    public async Task<IActionResult> AddContact(string? loggedUserId, [FromBody] ContactPostInput? bodyRequest)
    {
        if (loggedUserId == null || bodyRequest == null)
        {
            return BadRequest();
        }

        var newContactObj = users1DbService.AddContact(loggedUserId, bodyRequest.id, bodyRequest.name, bodyRequest.server);
        if (newContactObj == null){
            return NotFound();
        }
        await _messageHub.Clients.All.ReceiveUpdate(true);
        return Created(serverPath +"/api/contacts/"+bodyRequest.id+"/?loggedUserId="+loggedUserId,newContactObj);
    }

    [HttpGet("contacts/{id}")]
    public IActionResult GetContactById(string id, string? loggedUserId)
    {
        if (loggedUserId == null)
        {
            return BadRequest();
        }
        var output = users1DbService.GetOneContact(loggedUserId, id);
        if (output == null)
        {
            return NotFound();
        }
        return Ok(JsonSerializer.Serialize(output));
    }
    [HttpPut("contacts/{id}")]
    public async Task<IActionResult> UpdateContact(string id, string? loggedUserId,[FromBody] ContactPutInput? input)
    {
        if (loggedUserId == null || input == null)
        {
            return BadRequest();
        }
        if (!users1DbService.UpdateContactNameServer(loggedUserId,id,input.name,input.server))
        {
            return NotFound();
        }
        await _messageHub.Clients.All.ReceiveUpdate(true);
        return NoContent();
    }
    [HttpDelete("contacts/{id}")]
    public async Task<IActionResult> DeleteContact(string id, string? loggedUserId)
    {
        if (loggedUserId == null)
        {
            return BadRequest();
        }
        if (!users1DbService.DeleteContact(loggedUserId,id))
        {
            return NotFound();
        }
        await _messageHub.Clients.All.ReceiveUpdate(true);
        return NoContent();
    }
    [HttpDelete("android/{id}")]
    public  IActionResult DeleteContact(string? id)
    {
        if (id == null)
        {
            return BadRequest();
        }
        if (!users1DbService.DeleteAndroidUser(id))
        {
            return NotFound();
        }
        return NoContent();
    }
    [HttpGet("contacts/{id}/messages")]
    public IActionResult GetMessages(string id, string? loggedUserId)
    {
        if (loggedUserId == null)
        {
            return BadRequest();
        }
        var output = users1DbService.GetMessages(loggedUserId,id);
        if (output == null)
        {
            return NotFound();
        }
        return Ok(JsonSerializer.Serialize(output));
    }
    [HttpPost("contacts/{id}/messages")]
    public async Task<IActionResult> PostMessage(string id, string? loggedUserId,[FromBody] MessageInput? input)
    {
        if (loggedUserId == null || input == null)
        {
            return BadRequest();
        }
        var newMsgObj = users1DbService.AddMessage(loggedUserId, id, input.content,true);
        if (newMsgObj == null)
        {
            return NotFound();
        }
        await _messageHub.Clients.All.ReceiveUpdate(true);
        return Created(serverPath+"/api/contacts/" +id+"/messages/"+newMsgObj.id+"?loggedUserId="+loggedUserId,newMsgObj);
    }

    [HttpGet("contacts/{id}/messages/{id2}")]
    public IActionResult GetMessageById(string id, string id2, string? loggedUserId)
    {
        int msgId;
        if (loggedUserId == null || !int.TryParse(id2, out msgId))
        {
            return BadRequest();
        }

        var output = users1DbService.GetMessageById(loggedUserId, id, msgId);
        if (output == null)
        {
            return NotFound();
        }
        return Ok(JsonSerializer.Serialize(output));
    }
    [HttpPut("contacts/{id}/messages/{id2}")]
    public async Task<IActionResult> UpdateMessageById(string id, string id2, string? loggedUserId, [FromBody] MessageInput? requestBody)
    {
        int msgId;
        if (loggedUserId == null ||requestBody==null|| !int.TryParse(id2, out msgId))
        {
            return BadRequest();
        }
        string content = requestBody.content;
        if (!users1DbService.UpdateMessageContentById(loggedUserId,id,msgId,content))
        {
            return NotFound();
        }
        await _messageHub.Clients.All.ReceiveUpdate(true);
        return NoContent();
    }
    [HttpDelete("contacts/{id}/messages/{id2}")]
    public async Task<IActionResult> DeleteMessageById(string id, string id2, string? loggedUserId)
    {
        int msgId;
        if (loggedUserId == null ||!int.TryParse(id2, out msgId))
        {
            return BadRequest();
        }
        if (!users1DbService.DeleteMessageById(loggedUserId,id,msgId))
        {
            return NotFound();
        }
        await _messageHub.Clients.All.ReceiveUpdate(true);
        return NoContent();
    }
    [HttpPost("invitations")]
    public async Task<IActionResult> PostInvitation([FromBody] InvitationInput? input)
    {
        if (input == null)
        {
            return BadRequest();
        }
        //post contact - id name server
        var postInput = new ContactPostInput();
        postInput.id = input.from;
        postInput.name = input.from;
        postInput.server = input.server;
        return await AddContact(input.to, postInput);
    }
    [HttpPost("transfer")]
    public async Task<IActionResult> PostTransfer([FromServices] IAndroidMessaging androidService,[FromBody] TransferInput? input) 
    {
        if (input == null)
        {
            return BadRequest();
        }
        //post message - content
        var postInput = new MessageInput();
        postInput.content = input.content;
        var msgObj = users1DbService.AddMessage(input.to, input.from, input.content, false);
        if (msgObj == null)
        {
            return BadRequest();
        }

        if (users1DbService.IsAndroidUser(input.to))
        {
            var token = users1DbService.GetAndroidToken(input.to);
            if (token != null)
            {
                string title = "A new message arrived";
                string desc = "From " + input.from + ": " + input.content;
                
                await androidService.SendNotification(token, title, desc);
            }
           
        }
        await _messageHub.Clients.All.ReceiveUpdate(true);
        return Created(serverPath+"/api/contacts/" +input.from+"/?loggedUserId="+input.to,msgObj);
    }
    
}