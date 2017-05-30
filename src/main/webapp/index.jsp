<%@ page import="nl.sogyo.mancala.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
   <head>
       <title>Mancala Game</title>
       <link href="${pageContext.request.contextPath}/css/style.css" type="text/css" rel="stylesheet"/>

   </head>
   <body>
        <% int[] gameBoardState = (int[]) session.getAttribute("gameBoard"); %>
        <% String formatPlayer1 = (String) session.getAttribute("formatPlayer1"); %>
        <% String formatPlayer2 = (String) session.getAttribute("formatPlayer2"); %>
        <% String winningPlayer = (String) session.getAttribute("Winner"); %>
        <% String gameOver = (String) session.getAttribute("gameOver"); %>


        <div class="boxed-layout">
            <div class="page-content">
                <div class="kalaha-container">
                    <div class="kalaha" id="kalaha2"><%= gameBoardState[13] %></div>
                </div>

                <div class="pit-container">
                    <div class="pit <%= formatPlayer2%>" id="pit13"><a href="?value=12"><%= gameBoardState[12] %></a></div>
                    <div class="pit <%= formatPlayer1%>" id="pit1"><a href="?value=0"><%= gameBoardState[0] %></a></div>
                </div>

                <div class="pit-container">
                    <div class="pit <%= formatPlayer2%>" id="pit12"><a href="?value=11"><%= gameBoardState[11] %></a></div>
                    <div class="pit <%= formatPlayer1%>" id="pit2"><a href="?value=1"><%= gameBoardState[1] %></a></div>
                </div>

                <div class="pit-container">
                    <div class="pit <%= formatPlayer2%>" id="pit11"><a href="?value=10"><%= gameBoardState[10] %></a></div>
                    <div class="pit <%= formatPlayer1%>" id="pit3"><a href="?value=2"><%= gameBoardState[2] %></a></div>
                </div>

                <div class="pit-container">
                    <div class="pit <%= formatPlayer2%>" id="pit10"><a href="?value=9"><%= gameBoardState[9] %></a></div>
                    <div class="pit <%= formatPlayer1%>" id="pit4"><a href="?value=3"><%= gameBoardState[3] %></a></div>
                </div>

                <div class="pit-container">
                    <div class="pit <%= formatPlayer2%>" id="pit9"><a href="?value=8"><%= gameBoardState[8] %></a></div>
                    <div class="pit <%= formatPlayer1%>" id="pit5"><a href="?value=4"><%= gameBoardState[4] %></a></div>
                </div>

                <div class="pit-container">
                    <div class="pit <%= formatPlayer2%>" id="pit8"><a href="?value=7"><%= gameBoardState[7] %></a></div>
                    <div class="pit <%= formatPlayer1%>" id="pit6"><a href="?value=5"><%= gameBoardState[5] %></a></div>

                </div>

                <div class="kalaha-container">
                    <div class="kalaha" id="kalaha1"><%= gameBoardState[6] %></div>
                </div>


            </div>

            <div class="winner"><%=winningPlayer%></div>
            <div class="restart <%=gameOver%>"><a href="?restart=true">Restart?</a></div>
        </div>

</body>
</html>