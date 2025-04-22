// chart-handler.js
document.addEventListener("DOMContentLoaded", function() {
    // Check if chart element exists
    const budgetChartElement = document.getElementById('budgetChart');
    if (!budgetChartElement) return;
    
    // Get chart data from embedded script tag
    const chartDataElement = document.getElementById('chartData');
    
    try {
        // Parse the JSON data
        const chartData = JSON.parse(chartDataElement.textContent || '{}');
        
        // Create arrays for labels and data
        const labels = [];
        const spent = [];
        const limits = [];
        const remainingAmounts = [];
        
        // Process chart data
        if (chartData && chartData.length) {
            chartData.forEach(item => {
                labels.push(item.category);
                spent.push(parseFloat(item.spent));
                limits.push(parseFloat(item.limit));
                remainingAmounts.push(parseFloat(item.remaining));
            });
            
            // Create the chart
            new Chart(budgetChartElement, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Spent',
                            data: spent,
                            backgroundColor: 'rgba(255, 99, 132, 0.7)',
                            borderColor: 'rgb(255, 99, 132)',
                            borderWidth: 1
                        },
                        {
                            label: 'Budget Limit',
                            data: limits,
                            backgroundColor: 'rgba(54, 162, 235, 0.7)',
                            borderColor: 'rgb(54, 162, 235)',
                            borderWidth: 1
                        }
                    ]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Amount'
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'Category'
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        title: {
                            display: true,
                            text: 'Budget vs Actual Spending'
                        }
                    }
                }
            });
        }
    } catch (e) {
        console.error("Error initializing budget chart:", e);
    }
});