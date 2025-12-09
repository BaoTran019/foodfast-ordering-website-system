import { useContext, useMemo } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer
} from "recharts";
import { OrdersContext } from "../../contexts/OrdersContext";

/*const data = [
  { name: "Tháng 1", doanhThu: 4000 },
  { name: "Tháng 2", doanhThu: 3000 },
  { name: "Tháng 3", doanhThu: 5000 },
  { name: "Tháng 4", doanhThu: 2780 },
  { name: "Tháng 5", doanhThu: 5890 },
  { name: "Tháng 6", doanhThu: 6390 },
  { name: "Tháng 7", doanhThu: 7490 },
  { name: "Tháng 8", doanhThu: 5590 },
  { name: "Tháng 9", doanhThu: 8690 },
  { name: "Tháng 10", doanhThu: 7890 },
  { name: "Tháng 11", doanhThu: 7690 },
  { name: "Tháng 12", doanhThu: 5590 },
];*/

function RevenueLineChart() {

  const { orders } = useContext(OrdersContext);

  // 🧮 Tính doanh thu theo tháng (chỉ lấy đơn Completed)
  const data = useMemo(() => {
    // Khởi tạo mảng 12 tháng mặc định
    const monthlyRevenue = Array.from({ length: 12 }, (_, i) => ({
      name: `Tháng ${i + 1}`,
      doanhThu: 0
    }));

    orders
      .filter(order => order.status === "Completed") // chỉ đơn hoàn tất
      .forEach(order => {
        const month = new Date(order.orderDate).getMonth(); // 0–11
        monthlyRevenue[month].doanhThu += order.totalPrice;
      });

    return monthlyRevenue;
  }, [orders]);

  return (
    <div style={{ width: "100%", height: 460 }}>
      <ResponsiveContainer>
        <LineChart data={data} margin={{ top: 20, right: 30, bottom: 5, left: 0 }}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line
            type="monotone"
            dataKey="doanhThu"
            stroke="#1492dbff"
            strokeWidth={2}
            dot={{ r: 4 }}
            activeDot={{ r: 6 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

export default RevenueLineChart;
