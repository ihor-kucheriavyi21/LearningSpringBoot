<#import "parts/common.ftlh" as c>

<@c.page>
    List of users

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as someuser>
            <tr>
                <td>${someuser.username}</td>
                <td><#list someuser.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${someuser.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>