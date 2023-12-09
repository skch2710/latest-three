<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<title>Table with Colored Header</title>
	<style>
		table,th,td {
			border: 1px solid black;
			border-collapse: collapse;
		}

		th {
			background-color: #eee;
			padding: 5px;
			text-align: left;
		}

		/* Style for the second table without borders */
		table.no-border,table.no-border th,table.no-border td {
			border: none;
			border-collapse: collapse;
		}
	</style>
</head>

<body>
	<h2>Table Data</h2>
	<table width="80%">
		<thead>
			<tr>
				<th>Name</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
			<#list studentList as student>
				<tr>
					<td>${student.fullName}</td>
					<td>${student.emailId}</td>
				</tr>
			</#list>
		</tbody>
	</table>
	<p></p>
	<!-- Table Signature without Borders -->
	<table class="no-border" width="100%">
		<tbody>
			<tr>
				<td>Thank You,</td>
			</tr>
			<tr>
				<td>Sathish CH</td>
			</tr>
			<tr>
				<td><a href="mailto:skch2710@outlook.com">skch2710@outlook.com</a></td>
			</tr>
			<tr>
				<td><img src="cid:signature" alt="signature" width="200" height="60"></td>
			</tr>
		</tbody>
	</table>
</body>

</html>