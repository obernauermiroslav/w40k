I created this simple java game, where you can create and delete ships and then fight with them, you simply choose one ship, then computer will choose one random ship and both of you will roll virtual dice, If you win, enemy ship will be destroyed, if you loose , your ship will be destroyed, the fight can continue until one side has no more ships.
Added new game, where you can fight with your ship against enemy ships and also you can upgrade your ship.
Still in progress, will continue with more features

Launch with : W40kApplication

docker build -t w40k-image .

docker run -p 8080:8080 w40k-image
