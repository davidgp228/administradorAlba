using System;
using mqttprueba.iOS;
using uPLibrary.Networking.M2Mqtt;

[assembly: Xamarin.Forms.Dependency(typeof(mqtt))]
namespace mqttprueba.iOS
{
    public class mqtt : sendmqtt
    {
        private bool status = false;
        MqttClient mqttClient;
        string clientId;

        public bool enviarmqtt(String broker, String topico, String mensaje)
        {
            try
            {

                if (mqttClient != null && mqttClient.IsConnected)
                {
                    //**publicar
                    System.Diagnostics.Debug.Print("Publicar... ");
                    mqttClient.Publish(topico, System.Text.Encoding.UTF8.GetBytes(mensaje));
                }
                else
                {

                    mqttClient = new MqttClient(broker);

                    // use a unique id as client id, each time we start the application
                    clientId = Guid.NewGuid().ToString();

                    mqttClient.Connect(clientId);

                    //mqttClient.Connect (customerDB.customerId+"", Mqtt_Username, Mqtt_Password, false, KeepAlives);
                    if (mqttClient.IsConnected)
                    {
                        System.Diagnostics.Debug.Print("Conectado... ");

                        //**publicar
                        mqttClient.Publish(topico, System.Text.Encoding.UTF8.GetBytes(mensaje));

                        status = true;
                    }
                }

            }
            catch (Exception e)
            {
                System.Diagnostics.Debug.Print("Error M2Mqtt ! " + e);

                status = false;
            }

            return status;
        }

        public void desconectar()
        {

            if (mqttClient.IsConnected)
            {
                mqttClient.Disconnect();

                System.Diagnostics.Debug.Print("Desconectado... ");
            }
        }

    }
}
