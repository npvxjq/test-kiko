<html>
<body>
<h2>kiko.homes test task</h2>
<br/>Author: Pavel Nokhrin
<br/>
<br/>
<br/>
<li><a href="rest/flats.json">rest/flats.json</a></li>
<li><a href="rest/tenants.json">rest/tenants.json</a></li>
<li><a href="rest/occupations.json">rest/occupations.json</a></li>
<li><a href="rest/viewings.json">rest/viewings.json</a></li>
<li><a href="rest/cmd?action=requestViewing&tenant=1&flat=1&slot=5">rest/cmd?action=requestViewing&tenant=1&flat=1&slot=5</a></li>
<li><a href="rest/cmd?action=cancelRequestViewing&tenant=1&viewing=1">rrest/cmd?action=cancelRequestViewing&tenant=1&viewing=5</a></li>
<li><a href="rest/cmd?action=acceptViewing&tenant=1&viewing=1">rest/cmd?action=acceptViewing&tenant=1&viewing=5</a></li>
<li><a href="rest/cmd?action=rejectViewing&tenant=1&viewing=1">rest/cmd?action=rejectViewing&tenant=1&viewing=5</a></li>
<br/><br/>
<form method="get" action="rest/cmd">
    <br/>action<select name="action">
    <option>requestViewing</option>
    <option>cancelRequestViewing</option>
    <option>acceptViewing</option>
    <option>rejectViewing</option>
    </select>
    <br/>tenant<input type="text" name="tenant" value="1"/>
    <br/>flat<input type="text" name="flat" value="1"/>
    <br/>slot<input type="text" name="slot" value="6"/>
    <br/>viewing<input type="text" name="viewing" value="1"/>
    <br/><input type="submit">
</form>

</form>
</body>
</html>
